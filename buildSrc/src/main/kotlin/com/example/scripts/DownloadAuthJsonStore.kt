package com.example.scripts.screenshots

import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URL
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

open class DownloadAuthJsonStore : DefaultTask() {

    @Input
    lateinit var storeAuthKeyFileUrl: String

    @OutputDirectory
    lateinit var outputDirectory: File

    @TaskAction
    fun downloadAuthJson() {
        val url = URL(storeAuthKeyFileUrl)
        val inputStream: InputStream = url.openStream()
        val os: OutputStream =
            FileOutputStream(File(outputDirectory, STORE_KEY_NAME))

        inputStream.use { input ->
            os.use { output ->
                input.copyTo(output)
            }
        }
    }

    companion object {
        private const val STORE_KEY_NAME = "storeKey.json"
    }
}
