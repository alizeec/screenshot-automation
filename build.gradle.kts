import com.example.scripts.screenshots.DownloadAuthJsonStore
import com.example.scripts.screenshots.GenerateFinalScreenshots
import com.example.scripts.screenshots.RenameAndUploadRawScreenshots

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

val firebaseKey: String by project
task<RenameAndUploadRawScreenshots>("processRawScreenshots") {
    if (project.hasProperty("firebaseKey")) {
        firebaseStorageKey = firebaseKey
    }

    outputDirectory = project.file(".")
}

val storeAuthKey: String by project
task<DownloadAuthJsonStore>("downloadAuthJsonStore") {
    if (project.hasProperty("storeAuthKey")) {
        storeAuthKeyFileUrl = storeAuthKey
    }

    outputDirectory = project.file(".")
}
