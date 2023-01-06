package com.tapdeveloper.themoviedb.presentation.moviesDetail.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tapdeveloper.themoviedb.domain.model.Movie

@Composable
fun MovieTitleAndYear(selectedMovie: Movie, contentColor: Color) {
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
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Center
        )
        Text(
            text = selectedMovie.getReleaseYearText(),
            color = contentColor,
            textAlign = TextAlign.Center
        )
    }
}
