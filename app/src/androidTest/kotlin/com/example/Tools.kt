package com.example.common

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import java.lang.Thread.sleep

object Tools {

    private fun ViewInteraction.isDisplayed(): Boolean {
        return try {
            check(matches(ViewMatchers.isDisplayed()))
            true
        } catch (e: Throwable) {
            false
        }
    }

    private fun ViewInteraction.isClickable(): Boolean {
        return try {
            check(matches(ViewMatchers.isClickable()))
            true
        } catch (e: Throwable) {
            false
        }
    }

    fun isElementDisplayed(element: Int): Boolean {
        return onView(withId(element)).isDisplayed()
    }

    fun isElementClickable(element: Int): Boolean {
        return onView(withId(element)).isClickable()
    }

    fun waitElementIsClickable(element: Int) {
        var loopbreak = 0
        do {
            sleep(2000)
            loopbreak += 1
        } while (!isElementClickable(element) && loopbreak < 5)
    }

    fun waitElementIsDisplayed(element: Int) {
        var loopbreak = 0
        do {
            sleep(2000)
            loopbreak += 1
        } while (!isElementDisplayed(element) && loopbreak < 5)
    }
}
