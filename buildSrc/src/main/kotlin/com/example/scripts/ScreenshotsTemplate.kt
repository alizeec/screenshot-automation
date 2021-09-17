package com.example.scripts.screenshots

internal fun GenerateFinalScreenshots.screenshotTemplate(
    title: String,
    hideCat: Boolean,
    image: String,
    fontId: String
) = """
    {
      "elements": {
        "root": {
          "background_color": "#000000"
        },
        "sticker_cat": {
          "image_url": "imageUrl.png",
          "fitting_type": "fill",
          "alignment": "top left",
          "hidden": $hideCat
        },
        "frame": {
          "image_url": "imageUrl.png",
          "fitting_type": "fill",
          "alignment": "middle center"
        },
        "screenshot": {
          "image_url": "$image",
          "fitting_type": "fill",
          "alignment": "top left"
        },
        "text": {
          "payload": "$title",
          "color": "#FFFFFF",
          "font_size": 100,
          "font": "$fontId",
          "font_weight": 900,
          "line_height": 130,
          "alignment": "top center"
        }
      }
    }
""".trimIndent()
