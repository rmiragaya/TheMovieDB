package com.tapdeveloper.themoviedb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.tapdeveloper.themoviedb.presentation.moviesList.MoviesViewmodel
import com.tapdeveloper.themoviedb.presentation.navigation.Navigation
import com.tapdeveloper.themoviedb.ui.theme.TheMovieDBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MoviesViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.moviesResponse.movies.isEmpty()
            }
        }

        setContent {
            TheMovieDBTheme {
                Navigation(navController = rememberNavController())
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadNextMovies()
        viewModel.getFavoritesMovies()
    }
}
