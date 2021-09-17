package com.example.screenshots.utils

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.features.launcher.LauncherActivity
import com.schibsted.spain.barista.rule.flaky.FlakyTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import tools.fastlane.screengrab.locale.LocaleTestRule

/**
 * Convenient Base class to extend for UI tests used to take screenshots.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
abstract class BaseScreenshotTest {

    // region Data
    private val context by lazy { InstrumentationRegistry.getInstrumentation().targetContext }
    private val intentToLaunch = Intent(context, LauncherActivity::class.java) // we always want to start from it
    // endregion

    // / region Rules
    private lateinit var activityRule: ActivityScenario<LauncherActivity>

    @Rule @JvmField
    val localeTestRule = LocaleTestRule()

    private val flakyRule = FlakyTestRule().apply { allowFlakyAttemptsByDefault(3) }

    @get:Rule
    val chain: RuleChain = RuleChain.outerRule(flakyRule).around(localeTestRule)

    @Before
    open fun setup() {
        activityRule = ActivityScenario.launch(intentToLaunch)
    }
}
