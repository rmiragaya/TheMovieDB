package com.tapdeveloper.themoviedb

import android.content.Context
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToString
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.tapdeveloper.themoviedb.presentation.moviesList.MovieListScreen
import com.tapdeveloper.themoviedb.presentation.moviesList.MoviesViewmodel
import com.tapdeveloper.themoviedb.presentation.moviesList.composables.favoritedEmptyListTag
import com.tapdeveloper.themoviedb.presentation.moviesList.composables.favoritedMovieCardTag
import com.tapdeveloper.themoviedb.presentation.moviesList.composables.favoritesTitleTag
import com.tapdeveloper.themoviedb.presentation.moviesList.composables.searchBox
import com.tapdeveloper.themoviedb.presentation.moviesList.composables.searchIconTag
import com.tapdeveloper.themoviedb.util.FakeMovieRepo
import com.tapdeveloper.themoviedb.util.MoviesListMock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieListScreenTest {

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val title = context.getString(R.string.toolbar_title)
    private val cancelButton = context.getString(R.string.cancel)
    private val viewmodel = MoviesViewmodel(FakeMovieRepo())

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MovieListScreen(
                navController = rememberNavController(),
                viewModel = viewmodel
            )
        }
    }

    @Test
    fun toolbar_visibility_when_click_search() {
        // toolbar when there is no search bar
        composeTestRule.onNodeWithText(title).assertExists()
        composeTestRule.onNodeWithTag(searchBox).assertDoesNotExist()
        composeTestRule.onNodeWithText(cancelButton).assertDoesNotExist()
        composeTestRule.onNodeWithTag(searchIconTag).assertExists().performClick()

        // toolbar with search bar
        composeTestRule.onNodeWithText(title).assertDoesNotExist()
        composeTestRule.onNodeWithTag(searchIconTag).assertDoesNotExist()
        composeTestRule.onNodeWithTag(searchBox).assertExists()
        composeTestRule.onNodeWithText(cancelButton).assertExists().performClick()
    }

    @Test
    fun favorites_visibility_when_search() {
        // favorites tittle and empty card should be shown
        composeTestRule.onNodeWithTag(favoritesTitleTag).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(favoritedEmptyListTag).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(favoritedMovieCardTag).assertDoesNotExist()

        // open search and add input
        composeTestRule.onNodeWithTag(searchIconTag).assertExists().performClick()
        composeTestRule.onNodeWithTag(searchBox).performTextInput("xxx")

        // favorites tittle and cards should be gone
        composeTestRule.onNodeWithTag(favoritesTitleTag).assertDoesNotExist()
        composeTestRule.onNodeWithTag(favoritedEmptyListTag).assertDoesNotExist()
        composeTestRule.onNodeWithTag(favoritedMovieCardTag).assertDoesNotExist()

        // cancel the search
        composeTestRule.onNodeWithText(cancelButton).assertExists().performClick()

        // favorites tittle and empty card should be shown again
        composeTestRule.onNodeWithTag(favoritesTitleTag).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(favoritedEmptyListTag).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(favoritedMovieCardTag).assertDoesNotExist()
    }

    @Test
    fun add_and_remove_movies_from_favorites(){
        // favorites should be empty
        composeTestRule.onNodeWithTag(favoritedEmptyListTag).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(favoritedMovieCardTag).assertDoesNotExist()

        // add one movie
        viewmodel.selectMovie(MoviesListMock.getMovies()[0])
        viewmodel.addOrRemoveFavorites()

        // favorites should be one
        composeTestRule.onNodeWithTag(favoritedEmptyListTag).assertDoesNotExist()
        composeTestRule.onNodeWithTag(favoritedMovieCardTag).assertExists().assertIsDisplayed()
        composeTestRule.onAllNodesWithTag(favoritedMovieCardTag).assertCountEquals(1)

        // add one more movie
        viewmodel.selectMovie(MoviesListMock.getMovies()[1])
        viewmodel.addOrRemoveFavorites()

        // favorites should be two
        composeTestRule.onAllNodesWithTag(favoritedMovieCardTag).assertCountEquals(2)

        // remove one movie
        viewmodel.selectMovie(MoviesListMock.getMovies()[0])
        viewmodel.addOrRemoveFavorites()

        // favorites should be one
        composeTestRule.onAllNodesWithTag(favoritedMovieCardTag).assertCountEquals(1)

        // remove one more movie
        viewmodel.selectMovie(MoviesListMock.getMovies()[1])
        viewmodel.addOrRemoveFavorites()

        // favorites should empty
        composeTestRule.onNodeWithTag(favoritedEmptyListTag).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(favoritedMovieCardTag).assertDoesNotExist()
    }


    /** show tree, for debug */
    private fun print() {
        composeTestRule.waitForIdle()
        println("-----")
        println(composeTestRule.onRoot().printToString())
        println("-----")
    }
}
