package com.tapdeveloper.themoviedb.presentation.moviesDetail.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tapdeveloper.themoviedb.R
import com.tapdeveloper.themoviedb.domain.model.Movie

@Composable
fun MovieDescription(movie: Movie, contentColor: Color) {
    Text(
        modifier = Modifier
            .padding(top = 40.dp, start = 32.dp, end = 32.dp),
        text = stringResource(R.string.overview),
        color = contentColor
    )
    Text(
        modifier = Modifier
            .padding(vertical = 24.dp, horizontal = 32.dp),
        textAlign = TextAlign.Start,
        text = movie.overview ?: "",
        color = contentColor,
        style = MaterialTheme.typography.body2,
        lineHeight = 30.sp
    )
}
