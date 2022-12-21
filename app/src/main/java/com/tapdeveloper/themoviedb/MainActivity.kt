package com.tapdeveloper.themoviedb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
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
        viewModel.loadNextMovies()
        viewModel.getFavoritesMovies()
        setContent {
            TheMovieDBTheme {
                Navigation(navController = rememberNavController())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
}