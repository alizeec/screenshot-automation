package com.example.scripts.screenshots

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URL
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

open class GenerateFinalScreenshots : DefaultTask() {

    @InputFile
    lateinit var jsonInputFile: File

    @Input
    lateinit var abyssaleApiKey: String

    @Optional
    @Input
    var locales: String? = null

    @OutputDirectory
    lateinit var outputDirectoryForMainStore: File

    @OutputDirectory
    lateinit var outputDirectoryForPersonalizedStore: File

    private var counter = 1

    private val moshi by lazy {
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    private val client by lazy { OkHttpClient() }

    private val personalizedStoreCountries = listOf("en-IE", "fr-BE")

    @TaskAction
    fun fetchScreenshots() {
        println("⚙️ Configure screenshots")
        val countryConfigurationsJson = jsonInputFile.bufferedReader().use { it.readText() }
        val countryJsonAdapter = moshi.adapter(StoreScreenshots::class.java)
        val countyConfigurations = countryJsonAdapter.fromJson(countryConfigurationsJson)?.values

        val configs = mutableListOf<ScreenshotConfig>()
        countyConfigurations?.let {
            for (country in it) {
                val selectedLocales = locales
                val selectedLocalesParsed = selectedLocales?.replace("\"", "")?.replace("[", "")
                    ?.replace("]", "")?.split(",") ?: emptyList()

                if (selectedLocales.isNullOrBlank() || selectedLocalesParsed.contains(country.country)) {
                    for (screen in country.configs) {
                        val imageUrl = "$FIREBASE_STORAGE_PATH${screen.name}-${country.country}.png?alt=media"
                        val config = createTemplateScreenshotConfig(screen, country, imageUrl)
                        configs.add(config)
                    }
                    counter = 1
                }
            }
        }

        println("🚀 call Abyssale API")
        for (config in configs) {
            val templates = if (config is ScreenshotConfig.IntroConfig) {
                introTemplate(config.title)
            } else {
                screenshotTemplate(
                    config.title,
                    config.hideCat,
                    config.imageUrl,
                    config.fontId
                )
            }
            callAbyssale(templates, config)
        }
    }

    private fun createTemplateScreenshotConfig(
        screen: StoreScreenshotConfig,
        country: StoreScreenshotCountry,
        imageUrl: String
    ): ScreenshotConfig {
        val attributes = ConfigAttributes(
            name = "$counter-${screen.name}",
            title = screen.text,
            country = country.country,
            imageUrl = imageUrl,
            fontId = country.fontId
        )
        counter++
        return when (screen.name) {
            INTRO -> ScreenshotConfig.IntroConfig(attributes)
            HOME -> ScreenshotConfig.HomeConfig(attributes)
            SEARCH -> ScreenshotConfig.SearchConfig(attributes)
            else -> {
                println("Unknow type ${screen.name}")
                ScreenshotConfig.HomeConfig(attributes)
            }
        }
    }

    private fun callAbyssale(
        template: String,
        config: ScreenshotConfig
    ) {
        val templateId = if (config is ScreenshotConfig.IntroConfig) {
            TEMPLATE_INTRO_ID
        } else TEMPLATE_SCREENSHOTS_ID
        val request = Request.Builder()
            .url("https://api.abyssale.com/banner-builder/$templateId/generate")
            .post(template.toRequestBody())
            .addHeader("x-api-key", abyssaleApiKey)
            .addHeader("content-type", "application/json")
            .build()

        val response = client.newCall(request).execute()

        val body = response.body
        if (body != null && response.isSuccessful) {
            val jsonAdapter = moshi.adapter(AbyssaleResponse::class.java)
            val url = jsonAdapter.fromJson(body.string())?.image?.url

            println("🚀 API call SUCCEED ✅ ︎ Banner is $url")

            url?.let {
                downloadImage(url, config.name, config.country)
            }
        } else {
            throw RuntimeException("🚀 API call FAILED ⛑🔥 result=${response.code}")
        }
        response.close()
    }

    private fun downloadImage(imageUrl: String, nameFile: String, nameFolder: String) {
        val url = URL(imageUrl)
        val inputStream: InputStream = url.openStream()
        val folderPath = if (personalizedStoreCountries.contains(nameFolder)) {
            "$outputDirectoryForPersonalizedStore/$nameFolder"
        } else {
            "$outputDirectoryForMainStore/$nameFolder/images/phoneScreenshots"
        }

        if (!File(folderPath).exists()) {
            val folder = File(folderPath)
            folder.mkdirs()
        }
        val os: OutputStream = FileOutputStream(File(folderPath, "$nameFile.jpeg"))

        inputStream.use { input ->
            os.use { output ->
                input.copyTo(output)
            }
        }

        println("File $nameFile.jpeg saved ✅")
    }

    companion object {
        const val INTRO = "intro"
        const val HOME = "home"
        const val SEARCH = "search"
    }
}
