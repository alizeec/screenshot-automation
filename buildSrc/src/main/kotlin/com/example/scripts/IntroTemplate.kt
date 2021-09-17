package com.example.scripts.screenshots

internal fun GenerateFinalScreenshots.introTemplate(text: String) = """
    {
  "elements": {
    "root": {
      "background_color": "#000000"
    },
    "checked_certified": {
      "image_url": "imageUrl.png",
      "fitting_type": "fill",
      "alignment": "top left"
    },
    "refurbished": {
      "image_url": "imageUrl.png",
      "fitting_type": "fill",
      "alignment": "top left"
    },
    "sun": {
      "image_url": "imageUrl.png",
      "fitting_type": "fill",
      "alignment": "top left"
    },
    "text": {
      "payload": "$text",
      "color": "#ffffff",
      "font_size": 160,
      "font": "eddd-11ea-9c65-ce2ec6ff9351",
      "font_weight": 900,
      "line_height": 130,
      "alignment": "middle center"
    },
    "product": {
      "image_url": "imageUrl.png",
      "fitting_type": "fill",
      "alignment": "top left"
    }
  }
}
""".trimIndent()
