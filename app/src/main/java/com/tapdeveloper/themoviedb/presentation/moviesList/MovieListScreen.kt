package com.tapdeveloper.themoviedb.presentation.moviesList

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tapdeveloper.themoviedb.presentation.moviesList.composables.FavoritesLazyRow
import com.tapdeveloper.themoviedb.presentation.moviesList.composables.FavoritesMoviesRowTitle
import com.tapdeveloper.themoviedb.presentation.moviesList.composables.PaginationLoading
import com.tapdeveloper.themoviedb.presentation.moviesList.composables.RecommendedMovieCard
import com.tapdeveloper.themoviedb.presentation.moviesList.composables.RecommendedMoviesColumnTitle
import com.tapdeveloper.themoviedb.presentation.moviesList.composables.TopSearchBar
import com.tapdeveloper.themoviedb.presentation.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun MovieListScreen(navController: NavController, viewModel: MoviesViewmodel) {

    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            TopSearchBar(
                viewmodel = viewModel,
                onCancelSeacrh = {
                    coroutineScope.launch {
                        scrollState.animateScrollToItem(index = 0)
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding),
            state = scrollState
        ) {
            item {

                FavoritesSection(navController, viewModel)

                RecommendedMoviesColumnTitle()
            }

            itemsIndexed(viewModel.moviesResponse.movies) { index, movie ->
                if (viewModel.shouldFetchMoreMovies(index))
                    viewModel.loadNextMovies()

                RecommendedMovieCard(movie) {
                    viewModel.selectMovie(movie)
                    navController.navigate(Screen.MovieDetailScreen.route)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                if (viewModel.isLoadingColum) {
                    PaginationLoading()
                }
                if (!viewModel.columnError.isNullOrEmpty()) {
                    Toast.makeText(context, viewModel.columnError, Toast.LENGTH_SHORT).show()
                    viewModel.columnError = ""
                }
                if (!viewModel.rowError.isNullOrEmpty()) {
                    Toast.makeText(context, viewModel.rowError, Toast.LENGTH_SHORT).show()
                    viewModel.rowError = ""
                }
            }
        }
    }
}

@Composable
fun FavoritesSection(navController: NavController, viewModel: MoviesViewmodel) {
    AnimatedVisibility(
        visible = viewModel.searchQuery.isEmpty(),
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        FavoritesMoviesRowTitle()
    }

    AnimatedVisibility(
        visible = viewModel.searchQuery.isEmpty()
    ) {
        FavoritesLazyRow(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            movies = viewModel.favoritesMovies,
            isLoading = viewModel.isLoadingRow,
            onClickMovie = { movieSelected ->
                viewModel.selectMovie(movieSelected)
                navController.navigate(Screen.MovieDetailScreen.route)
            }
        )
    }
}
