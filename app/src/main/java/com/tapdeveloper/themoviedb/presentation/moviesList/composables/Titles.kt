package com.tapdeveloper.themoviedb.presentation.moviesList.composables

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tapdeveloper.themoviedb.R

@VisibleForTesting(otherwise = java.lang.reflect.Modifier.PRIVATE)
const val favoritesTitleTag = "favoritesTitleTag"

@VisibleForTesting(otherwise = java.lang.reflect.Modifier.PRIVATE)
const val recommendedTitleTag = "recommendedTitleTag"

@Composable
fun FavoritesMoviesRowTitle() {
    Text(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.horizontal_margin))
            .testTag(favoritesTitleTag),
        text = stringResource(R.string.favorites)
    )
}

@Composable
fun RecommendedMoviesColumnTitle() {
    Text(
        modifier = Modifier
            .padding(
                start = 16.dp,
                top = 20.dp,
                bottom = 16.dp
            )
            .testTag(recommendedTitleTag),
        text = stringResource(R.string.recommended)
    )
}
