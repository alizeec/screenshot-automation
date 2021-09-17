package com.example.scripts.screenshots

internal data class AbyssaleResponse(val banner: ResponseBanner, val image: ResponseImage)

internal data class ResponseBanner(val id: String)

internal data class ResponseImage(val type: String, val url: String)
