package com.tapdeveloper.themoviedb

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToString
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.tapdeveloper.themoviedb.presentation.moviesList.MovieListScreen
import com.tapdeveloper.themoviedb.presentation.moviesList.MoviesViewmodel
import com.tapdeveloper.themoviedb.presentation.moviesList.composables.favoritedEmptyListTag
import com.tapdeveloper.themoviedb.presentation.moviesList.composables.favoritedMovieCardTag
import com.tapdeveloper.themoviedb.presentation.moviesList.composables.favoritesTitleTag
import com.tapdeveloper.themoviedb.presentation.moviesList.composables.searchIconTag
import com.tapdeveloper.themoviedb.util.FakeMovieRepo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieListScreenTest {

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val title = context.getString(R.string.toolbar_title)

    @get:Rule
//    val composeTestRule = createAndroidComposeRule<MainActivity>() // todo sacarlo?
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MovieListScreen(
                navController = rememberNavController(),
                viewModel = MoviesViewmodel(FakeMovieRepo())
            )
        }
    }

    @Test
    fun titleShow() {
        composeTestRule.onNodeWithText(title).assertExists()
    }

    @Test
    fun titleVisibilityWhenClickSearch() {
        composeTestRule.onNodeWithText(title).assertExists()
        composeTestRule.onNodeWithTag(searchIconTag).assertExists().performClick()
        composeTestRule.onNodeWithText(title).assertDoesNotExist()
    }

    @Test
    fun favoritesVisibilitywhenSearch() {
        composeTestRule.onNodeWithTag(favoritesTitleTag).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(favoritedEmptyListTag).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(favoritedMovieCardTag).assertDoesNotExist()

    }


    /** show tree for debug */
    private fun print() {
        composeTestRule.waitForIdle()
        println("-----")
        println(composeTestRule.onRoot().printToString())
        println("-----")
    }
}
