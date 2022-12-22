package com.tapdeveloper.themoviedb.presentation.moviesList.composables

import androidx.annotation.VisibleForTesting
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tapdeveloper.themoviedb.R
import com.tapdeveloper.themoviedb.domain.model.Movie
import com.tapdeveloper.themoviedb.ui.theme.Purple500
import com.tapdeveloper.themoviedb.ui.theme.Shapes

@VisibleForTesting(otherwise = java.lang.reflect.Modifier.PRIVATE)
const val favoritedMovieCardTag = "favoritedMovieCardTag"
@VisibleForTesting(otherwise = java.lang.reflect.Modifier.PRIVATE)
const val favoritedEmptyListTag = "favoritedEmptyListTag"

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
                    FavoritesMovieCard(movie, onClickMovie)
                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacer)))
                }
            }
        }
    }
}

@Composable
fun EmptySubscriptionList() {
    Card(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.horizontal_margin))
            .fillMaxWidth()
            .height(140.dp)
            .testTag(favoritedEmptyListTag),
        shape = Shapes.medium,
        backgroundColor = Color.LightGray,
        elevation = 4.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.no_favorites),
                style = MaterialTheme.typography.body1,
                color = Purple500
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoritesMovieCard(movie: Movie, onClick: (Movie) -> Unit) {
    Card(
        modifier = Modifier
            .width(dimensionResource(id = R.dimen.favorite_card_width))
            .height(dimensionResource(id = R.dimen.favorite_card_height))
            .testTag(favoritedMovieCardTag),
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
