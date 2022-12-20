package com.tapdeveloper.themoviedb.presentation.moviesDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavController
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tapdeveloper.themoviedb.R
import com.tapdeveloper.themoviedb.domain.model.Movie
import com.tapdeveloper.themoviedb.presentation.moviesList.MoviesViewmodel
import com.tapdeveloper.themoviedb.ui.theme.Black
import com.tapdeveloper.themoviedb.ui.theme.White

@Composable
fun MovieDetailScreen(navController: NavController, viewModel: MoviesViewmodel) {
    val scrollState = rememberLazyListState()

    /** predominant color on the image */
    var mainColor by remember { mutableStateOf(Black) }

    /** adapt the color of the content to be readable */
    val contentColor = if (mainColor.luminance() >= 0.5f) Black else White

    with(viewModel) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            BackgroundImage(movie = selectedMovie, mainColor = mainColor) {
                mainColor = it
            }
        }
    }

}

@Composable
private fun BackgroundImage(movie: Movie, mainColor: Color, onMainColorChange: (Color) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(stringResource(id = R.string.image_base_url) + movie.posterPath)
                .crossfade(true)
                .allowHardware(false)
                .build(),
            placeholder = null,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            /** extract the predominant color so later change the content color*/
            onSuccess = { result ->
                Palette.Builder(result.result.drawable.toBitmap()).generate() { palette ->
                    palette?.dominantSwatch?.let {
                        onMainColorChange(Color(it.rgb))
                    }
                }
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = mainColor.copy(alpha = 0.75f),
                    shape = RectangleShape
                )
        )
    }
}
