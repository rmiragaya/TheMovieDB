package com.tapdeveloper.themoviedb.presentation.moviesDetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                MovieDescription(selectedMovie, contentColor)
                FavoritesButton(
                    subscribed = selectedMovie.wasSubscribed,
                    contentColor = contentColor,
                    mainColor = mainColor,
                    isLoading = isLoadingRow
                ) {
                    addOrRemoveFavorites()
                }
            }
            BackButton(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp),
                onClick = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun FavoritesButton(
    modifier: Modifier = Modifier,
    subscribed: Boolean,
    contentColor: Color,
    mainColor: Color,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = modifier.then(Modifier.fillMaxWidth(0.7f)),
            onClick = onClick,
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (subscribed) contentColor else Color.Transparent,
                contentColor = if (subscribed) mainColor else contentColor
            ),
            shape = CircleShape,
            border = if (subscribed) null else BorderStroke(2.dp, contentColor),
        ) {
            if (!isLoading) {
                Text(
                    modifier = Modifier.padding(horizontal = 28.dp, vertical = 8.dp),
                    text = if (subscribed) "Agregado!" else "Agregar a favoritos?",
                    color = if (subscribed) mainColor.copy(alpha = 0.7f) else contentColor
                )
            } else {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun MovieDescription(movie: Movie, contentColor: Color) {
    Text(
        modifier = Modifier
            .padding(top = 40.dp, start = 32.dp, end = 32.dp),
        text = "OVERVIEW",
        color = contentColor
    )
    Text(
        modifier = Modifier
            .padding(vertical = 24.dp, horizontal = 32.dp)
            .layoutId("description"),
        textAlign = TextAlign.Start,
        text = movie.overview ?: "",
        color = contentColor,
        style = MaterialTheme.typography.body1,
        lineHeight = 30.sp
    )
}

@Composable
private fun MovieTitleAndYear(selectedMovie: Movie, contentColor: Color) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = selectedMovie.title ?: "",
            color = contentColor,
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = selectedMovie.getReleaseYearText(),
            color = contentColor,
            textAlign = TextAlign.Center
        )
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
            /** extract the predominant color so later change the content color */
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

@Composable
private fun BackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    backgroundColor: Color = Black,
    contentColor: Color = White
) {
    Button(
        modifier = modifier.then(Modifier.size(32.dp)),
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Ir atrás")
    }
}

@Composable
private fun MoviePoster(movie: Movie) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .width(200.dp)
                .height(300.dp),
            elevation = 4.dp
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(stringResource(id = R.string.image_base_url) + movie.posterPath)
                    .crossfade(true)
                    .build(),
                placeholder = null,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
