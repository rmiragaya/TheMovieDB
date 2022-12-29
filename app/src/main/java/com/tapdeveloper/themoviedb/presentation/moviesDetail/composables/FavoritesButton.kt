package com.tapdeveloper.themoviedb.presentation.moviesDetail.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tapdeveloper.themoviedb.R

@Composable
fun FavoritesButton(
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
            .padding(top = 16.dp),
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
                    text = if (subscribed) stringResource(R.string.added) else stringResource(R.string.add),
                    color = if (subscribed) mainColor.copy(alpha = 0.7f) else contentColor
                )
            } else {
                CircularProgressIndicator()
            }
        }
    }
}
