package com.tapdeveloper.themoviedb

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToString
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.tapdeveloper.themoviedb.presentation.moviesList.composables.searchIconTag
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieListScreenTest {

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val title = context.getString(R.string.toolbar_title)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun titleShow(){
        composeTestRule.onNodeWithText(title).assertExists()
    }

    @Test
    fun titleDontShowWhenClickSearch(){
        composeTestRule.onNodeWithText(title).assertIsDisplayed()
        composeTestRule.onNodeWithTag(searchIconTag).assertExists().performClick()
        composeTestRule.onNodeWithText(title).assertDoesNotExist()
    }


    /** show tree for debug */
    private fun print() {
        composeTestRule.waitForIdle()
        println("-----")
        println(composeTestRule.onRoot().printToString())
        println("-----")
    }
}
