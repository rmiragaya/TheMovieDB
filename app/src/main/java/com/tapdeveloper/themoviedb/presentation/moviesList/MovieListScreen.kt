package com.tapdeveloper.themoviedb.presentation.moviesList

import android.util.Log
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tapdeveloper.themoviedb.presentation.moviesList.composables.FavoritesLazyRow
import com.tapdeveloper.themoviedb.presentation.moviesList.composables.PaginationLoading
import com.tapdeveloper.themoviedb.presentation.moviesList.composables.RecommendedMovieCard
import com.tapdeveloper.themoviedb.presentation.moviesList.composables.RecommendedMoviesColumnTitle
import com.tapdeveloper.themoviedb.presentation.moviesList.composables.SubscribedMoviesRowTitle
import com.tapdeveloper.themoviedb.presentation.moviesList.composables.TopSearchBar
import com.tapdeveloper.themoviedb.presentation.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun MovieListScreen(navController: NavController, viewModel: MoviesViewmodel) {

    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope() // todo for topbar

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            with(viewModel) {
                TopSearchBar(
                    title = "The Movie DB",
                    isSearching = isSearching,
                    searchQuery = searchQuery,
                    onQueryChange = {
                        Log.d("TAG", "onQueryChange: $it")
                        searchQuery = it
                        searchMovies()
                    },
                    cancelSearch = {
                        Log.d("TAG", "cancelSearch")
                        isSearching = false
                        cancelSearch()
                        coroutineScope.launch {
                            scrollState.animateScrollToItem(index = 0)
                        }
                    },
                    onClickSearchIcon = {
                        Log.d("TAG", "onClickSearchIcon")
                        isSearching = true
                    },
                    onDeleteSearch = {
                        Log.d("TAG", "onDeleteSearch")
                        searchQuery = ""
                    }
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding),
            state = scrollState
        ) {
            item {
                SubscribedMoviesRowTitle()
            }
            item {
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
            item {
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
            }
        }
    }
}
