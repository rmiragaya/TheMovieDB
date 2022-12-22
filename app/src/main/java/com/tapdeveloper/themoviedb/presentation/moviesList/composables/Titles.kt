package com.tapdeveloper.themoviedb.presentation.moviesList.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.tapdeveloper.themoviedb.R

@Composable
fun FavoritesMoviesRowTitle() {
    Text(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.horizontal_margin)),
        text = "FAVORITOS" // todo a string
    )
}

@Composable
fun RecommendedMoviesColumnTitle() {
    Text(
        modifier = Modifier.padding(
            start = 16.dp,
            top = 20.dp,
            bottom = 16.dp
        ),
        text = "RECOMENDADAS" // todo a string
    )
}
