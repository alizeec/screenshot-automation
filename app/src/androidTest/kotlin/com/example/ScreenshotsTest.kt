package com.example.screenshots.tests

import com.example.screenshots.pages.home
import com.example.screenshots.pages.landing
import com.example.screenshots.pages.product
import com.example.screenshots.utils.BaseScreenshotTest
import io.mockk.coEvery
import io.mockk.mockk
import java.util.Locale
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import tools.fastlane.screengrab.Screengrab

class ScreenshotsTest : BaseScreenshotTest() {

    @Test
    fun takeScreenshots() {
        home {
            goToHome()
            Screengrab.screenshot("home")

            goToSearch()
            Screengrab.screenshot("search")
        }
    }
}
