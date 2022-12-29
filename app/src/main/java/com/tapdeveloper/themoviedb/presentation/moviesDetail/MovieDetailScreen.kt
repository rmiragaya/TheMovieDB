package com.tapdeveloper.themoviedb.presentation.moviesDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tapdeveloper.themoviedb.presentation.moviesDetail.composables.BackButton
import com.tapdeveloper.themoviedb.presentation.moviesDetail.composables.BackgroundImage
import com.tapdeveloper.themoviedb.presentation.moviesDetail.composables.FavoritesButton
import com.tapdeveloper.themoviedb.presentation.moviesDetail.composables.MovieDescription
import com.tapdeveloper.themoviedb.presentation.moviesDetail.composables.MoviePoster
import com.tapdeveloper.themoviedb.presentation.moviesDetail.composables.MovieTitleAndYear
import com.tapdeveloper.themoviedb.presentation.moviesList.MoviesViewmodel
import com.tapdeveloper.themoviedb.ui.theme.Black
import com.tapdeveloper.themoviedb.ui.theme.White

@Composable
fun MovieDetailScreen(navController: NavController, viewModel: MoviesViewmodel) {
    val scrollState = rememberScrollState()

    /** predominant color on the image */
    var mainColor by remember { mutableStateOf(Black) }

    /** adapt the color of the content to be readable */
    val contentColor = if (mainColor.luminance() >= 0.5f) Black else White

    with(viewModel) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { padding ->
            BackgroundImage(movie = selectedMovie, mainColor = mainColor) {
                mainColor = it
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(padding)
                    .padding(top = 16.dp)
            ) {
                MoviePoster(movie = selectedMovie)
                MovieTitleAndYear(selectedMovie, contentColor)
                FavoritesButton(
                    subscribed = selectedMovie.wasSubscribed,
                    contentColor = contentColor,
                    mainColor = mainColor,
                    isLoading = isLoadingRow
                ) {
                    addOrRemoveFavorites()
                }
                MovieDescription(selectedMovie, contentColor)
            }
            BackButton(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp),
                onClick = { navController.popBackStack() }
            )
        }
    }
}
