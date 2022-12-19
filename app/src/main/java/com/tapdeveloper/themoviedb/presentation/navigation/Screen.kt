package com.tapdeveloper.themoviedb.presentation.navigation

//todo why sealed?
sealed class Screen(val route: String) {
    object MovieListScreen : Screen("movie_list_screen")
    object MovieDetailScreen : Screen("movie_detail_screen")
}