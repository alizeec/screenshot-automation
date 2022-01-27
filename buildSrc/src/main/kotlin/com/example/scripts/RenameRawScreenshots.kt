package com.example.scripts.screenshots

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

/**
 * Rename the raw screenshots so they have a predictable name
 */
open class RenameRawScreenshots : DefaultTask() {

    @OutputDirectory
    lateinit var outputDirectory: File

    @TaskAction
    fun renameAndUpload() {
        File(PATH_RAW_SCREENSHOTS).walk().forEach {
            if (it.isFile && it.isHidden.not() && it.name != "screenshots.html") {
                val source = Paths.get(it.path)
                val prefix = it.name.split("_")[0]
                val suffix = it.parentFile.parentFile.parentFile.name
                println("Rename file to $prefix-$suffix.png ✅")
                Files.move(
                        source,
                        source.resolveSibling("$prefix-$suffix.png"),
                        StandardCopyOption.REPLACE_EXISTING
                )
            }
        }
    }

    companion object {
        private const val PATH_RAW_SCREENSHOTS = "./fastlane/metadata/android"
    }
}
