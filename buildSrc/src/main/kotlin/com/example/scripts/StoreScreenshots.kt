package com.example.scripts.screenshots

internal data class StoreScreenshots(val values: List<StoreScreenshotCountry>)

internal data class StoreScreenshotCountry(
    val country: String,
    val fontId: String,
    val configs: List<StoreScreenshotConfig>
)

internal data class StoreScreenshotConfig(val name: String, val text: String)
