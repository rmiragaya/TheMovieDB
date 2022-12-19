package com.tapdeveloper.themoviedb.presentation.moviesList.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SubscribedMoviesRowTitle() {
    Text(
        modifier = Modifier.padding(16.dp),
        text = "FAVORITOS"
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
        text = "RECOMENDADAS"
    )
}
