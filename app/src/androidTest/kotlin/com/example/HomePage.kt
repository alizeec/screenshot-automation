package com.example.screenshots.pages

import com.example.R
import com.example.Tools.isElementDisplayed
import com.example.Tools.waitElementIsDisplayed
import com.schibsted.spain.barista.interaction.BaristaMenuClickInteractions.clickMenu

fun home(func: HomePage.() -> Unit) = HomePage().apply(func)

class HomePage {
    fun goToHome() {
        waitElementIsDisplayed(R.id.home)
        clickMenu(R.id.home)
        waitElementIsDisplayed(R.id.pagerCardCarousel)
    }

    fun goToSearch() {
        waitElementIsDisplayed(R.id.search)
        clickMenu(R.id.search)
        waitElementIsDisplayed(R.id.searchItemsList)
    }
}
