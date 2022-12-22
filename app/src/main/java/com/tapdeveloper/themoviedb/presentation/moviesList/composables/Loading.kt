package com.tapdeveloper.themoviedb.presentation.moviesList.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.tapdeveloper.themoviedb.R

@Composable
fun PaginationLoading() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.horizontal_margin)),
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}
