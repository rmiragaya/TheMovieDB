package com.tapdeveloper.themoviedb.moviedetail

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.tapdeveloper.themoviedb.R
import com.tapdeveloper.themoviedb.presentation.moviesDetail.MovieDetailScreen
import com.tapdeveloper.themoviedb.presentation.moviesList.MoviesViewmodel
import com.tapdeveloper.themoviedb.util.FakeMovieRepo
import com.tapdeveloper.themoviedb.util.MoviesListMock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDetailScreenTest {

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val viewmodel = MoviesViewmodel(FakeMovieRepo())
    private val selectedMovie = MoviesListMock.getSelectedMovie()

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        viewmodel.selectMovie(selectedMovie)

        composeTestRule.setContent {
            MovieDetailScreen(
                navController = rememberNavController(),
                viewModel = viewmodel
            )
        }
    }

    @Test
    fun visibility_of_composables_on_screen() {
        // all info on screen and button should said R.string.add
        composeTestRule.onNodeWithText(selectedMovie.title.toString()).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(selectedMovie.overview.toString()).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(selectedMovie.getReleaseYearText()).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.add)).assertExists().assertIsDisplayed()

        // add movie to favorites
        composeTestRule.onNodeWithText(context.getString(R.string.add)).performClick()

        // button should change to R.string.added
        composeTestRule.onNodeWithText(context.getString(R.string.added)).assertExists().assertIsDisplayed()
    }
}
