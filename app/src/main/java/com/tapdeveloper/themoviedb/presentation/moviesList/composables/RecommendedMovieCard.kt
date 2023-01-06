package com.tapdeveloper.themoviedb.presentation.moviesList.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tapdeveloper.themoviedb.R
import com.tapdeveloper.themoviedb.domain.model.Movie
import com.tapdeveloper.themoviedb.ui.theme.DarkBlue
import com.tapdeveloper.themoviedb.ui.theme.LightBlue
import com.tapdeveloper.themoviedb.ui.theme.Shapes

const val RecommendedMovieCardTestTag = "MovieCard"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecommendedMovieCard(movie: Movie, onClick: () -> Unit) {

    Card(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.horizontal_margin))
            .fillMaxWidth()
            .height(152.dp)
            .testTag(RecommendedMovieCardTestTag),
        shape = Shapes.medium,
        elevation = 4.dp,
        onClick = onClick
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(stringResource(id = R.string.image_base_url) + movie.posterPath)
                .crossfade(true)
                .build(),
            colorFilter = ColorFilter.tint(color = DarkBlue, BlendMode.Modulate),
            placeholder = null,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            Modifier.padding(dimensionResource(id = R.dimen.horizontal_margin)),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = movie.title ?: " ",
                style = MaterialTheme.typography.h6,
                color = LightBlue
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun RecommendedMovieCardPreview() {
    val movie = Movie(
        id = 12345L,
        overview = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        title = "Title Movie",
        releaseDate = "11/12/1987"
    )
    RecommendedMovieCard(
        movie = movie,
        onClick = {}
    )
}
