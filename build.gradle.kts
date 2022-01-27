import com.example.scripts.screenshots.DownloadAuthJsonStore
import com.example.scripts.screenshots.GenerateFinalScreenshots
import com.example.scripts.screenshots.RenameRawScreenshots

val apiKey: String by project
val countries: String by project
task<GenerateFinalScreenshots>("generateFinalScreenshots") {
    if (project.hasProperty("apiKey")) {
        abyssaleApiKey = apiKey
    }
    if (project.hasProperty("countries")) {
        locales = countries
    }
    jsonInputFile =
        project.file("buildSrc/src/main/kotlin/com/example/scripts/screenshots/store_screenshots_config.json")

    outputDirectoryForMainStore = project.file("fastlane/metadata/android")
    outputDirectoryForPersonalizedStore = project.file("fastlane/metadata/personalizedStore")
}

task<RenameRawScreenshots>("processRawScreenshots") {
    outputDirectory = project.file(".")
}

val storeAuthKey: String by project
task<DownloadAuthJsonStore>("downloadAuthJsonStore") {
    if (project.hasProperty("storeAuthKey")) {
        storeAuthKeyFileUrl = storeAuthKey
    }

    outputDirectory = project.file(".")
}
