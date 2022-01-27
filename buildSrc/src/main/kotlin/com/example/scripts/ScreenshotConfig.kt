package com.example.scripts.screenshots

internal sealed class ScreenshotConfig(
    open val name: String,
    open val country: String,
    open val title: String,
    val hideCat: Boolean = true,
    open val fontId: String,
    open val image: String
) {
    data class IntroConfig(val attributes: ConfigAttributes) : ScreenshotConfig(
        name = attributes.name,
        title = attributes.title,
        country = attributes.country,
        hideCat = false,
        image = attributes.image,
        fontId = attributes.fontId
    )
    data class HomeConfig(val attributes: ConfigAttributes) : ScreenshotConfig(
        name = attributes.name,
        title = attributes.title,
        country = attributes.country,
        hideCat = false,
        image = attributes.image,
        fontId = attributes.fontId
    )

    data class SearchConfig(val attributes: ConfigAttributes) : ScreenshotConfig(
        name = attributes.name,
        title = attributes.title,
        country = attributes.country,
        hideCat = true,
        image = attributes.image,
        fontId = attributes.fontId
    )
}

data class ConfigAttributes(
    val name: String,
    val title: String,
    val country: String,
    val image: String,
    val fontId: String
)
