package com.tapdeveloper.themoviedb.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tapdeveloper.themoviedb.presentation.moviesDetail.MovieDetailScreen
import com.tapdeveloper.themoviedb.presentation.moviesList.MovieListScreen
import com.tapdeveloper.themoviedb.presentation.moviesList.MoviesViewmodel

@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: MoviesViewmodel = hiltViewModel()
) {
    NavHost(navController = navController, startDestination = Screen.MovieListScreen.route) {
        composable(route = Screen.MovieListScreen.route) {
            MovieListScreen(navController = navController, viewModel = viewModel)
        }
        composable(route = Screen.MovieDetailScreen.route) {
            MovieDetailScreen(navController = navController, viewModel = viewModel)
        }
    }
}