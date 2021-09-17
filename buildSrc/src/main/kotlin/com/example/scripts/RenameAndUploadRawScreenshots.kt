package com.example.scripts.screenshots

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.StorageOptions
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

open class RenameAndUploadRawScreenshots : DefaultTask() {

    @Input
    lateinit var firebaseStorageKey: String

    @OutputDirectory
    lateinit var outputDirectory: File

    @TaskAction
    fun renameAndUpload() {
        renameFiles()
        uploadOnFirebase()
    }

    private fun renameFiles() {
        File(PATH_RAW_SCREENSHOTS).walk().forEach {
            if (it.isFile && it.isHidden.not() && it.name != "screenshots.html") {
                val source = Paths.get(it.path)
                val prefix = it.name.split("_")[0]
                val suffix = it.parentFile.parentFile.parentFile.name
                Files.move(source, source.resolveSibling("$prefix-$suffix.png"))
            }
        }
    }

    private fun uploadOnFirebase() {

        downloadFile(firebaseStorageKey)

        val storageOptions = StorageOptions.newBuilder()
            .setProjectId(PROJECT_ID)
            .setCredentials(GoogleCredentials.fromStream(FileInputStream("./$FIREBASE_STORAGE_KEY_NAME")))
            .build()
        val storage = storageOptions.service

        File(PATH_RAW_SCREENSHOTS).walk().forEach {
            if (it.isFile && it.isHidden.not() && it.name != "screenshots.html") {
                val blobId = BlobId.of(BUCKET_NAME, "$BUCKET_SUBDIRECTORY${it.name}")
                val blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/png").build()
                storage.create(blobInfo, Files.readAllBytes(Paths.get(it.path)))
                println("File ${it.path} uploaded to bucket $BUCKET_NAME as ${it.name}")
            }
        }
    }

    private fun downloadFile(fileUrl: String) {
        val url = URL(fileUrl)
        val inputStream: InputStream = url.openStream()
        val os: OutputStream = FileOutputStream(File(outputDirectory, FIREBASE_STORAGE_KEY_NAME))

        inputStream.use { input ->
            os.use { output ->
                input.copyTo(output)
            }
        }

        println("File $FIREBASE_STORAGE_KEY_NAME saved ✅")
    }
}
