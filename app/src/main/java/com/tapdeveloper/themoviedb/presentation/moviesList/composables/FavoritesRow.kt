package com.tapdeveloper.themoviedb.presentation.moviesList.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tapdeveloper.themoviedb.R
import com.tapdeveloper.themoviedb.domain.model.Movie
import com.tapdeveloper.themoviedb.ui.theme.Purple500
import com.tapdeveloper.themoviedb.ui.theme.Shapes
import com.tapdeveloper.themoviedb.ui.theme.White

@Composable
fun FavoritesLazyRow(
    modifier: Modifier,
    movies: List<Movie>,
    isLoading: Boolean,
    onClickMovie: (Movie) -> Unit
) {
    if (movies.isEmpty() && !isLoading) {
        EmptySubscriptionList()
    } else {
        LazyRow(modifier = modifier) {
            if (isLoading) {
                item {
                    Box(Modifier.fillMaxSize()) {
                        CircularProgressIndicator()
                    }
                }
            } else {
                items(movies) { movie ->
                    SubscribedMovieCard(movie, onClickMovie)
                    Spacer(modifier = Modifier.width(12.dp))
                }
            }
        }
    }

}

@Composable
fun EmptySubscriptionList() {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(140.dp),
        shape = Shapes.medium,
        backgroundColor = White,
        elevation = 4.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Aun no tienes seleccionados favoritos",
                style = MaterialTheme.typography.body1,
                color = Purple500
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SubscribedMovieCard(movie: Movie, onClick: (Movie) -> Unit) {
    Card(
        modifier = Modifier
            .width(92.dp)
            .height(152.dp),
        shape = Shapes.medium,
        elevation = 4.dp,
        onClick = {
            onClick(movie)
        }
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