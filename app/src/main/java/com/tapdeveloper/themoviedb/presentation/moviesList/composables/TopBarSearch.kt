package com.tapdeveloper.themoviedb.presentation.moviesList.composables

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.tapdeveloper.themoviedb.R
import com.tapdeveloper.themoviedb.presentation.moviesList.MoviesViewmodel
import com.tapdeveloper.themoviedb.ui.theme.Grey200

@VisibleForTesting(otherwise = java.lang.reflect.Modifier.PRIVATE)
const val topSearchBarTag = "topSearchBarTag"
@VisibleForTesting(otherwise = java.lang.reflect.Modifier.PRIVATE)
const val searchIconTag = "searchIconTag"
@VisibleForTesting(otherwise = java.lang.reflect.Modifier.PRIVATE)
const val searchBox = "searchBox"

@Composable
fun TopSearchBar(
    viewmodel: MoviesViewmodel,
    onCancelSeacrh: () -> Unit,
) {
    TopAppBar(
        modifier = Modifier.testTag(topSearchBarTag),
        backgroundColor = Color.Transparent
    ) {
        with(viewmodel) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!isSearching) {
                        SearchIcon { isSearching = true }
                        Text(
                            modifier = Modifier.padding(horizontal = 12.dp),
                            text = stringResource(R.string.toolbar_title),
                            color = MaterialTheme.colors.primary
                        )
                    } else {
                        SearchBox(
                            modifier = Modifier
                                .padding(start = 16.dp, bottom = 12.dp)
                                .fillMaxWidth(0.8f)
                                .testTag(searchBox),
                            value = searchQuery,
                            onValueChange = {
                                searchQuery = it
                                searchMovies()
                            },
                            onDelete = { searchQuery = "" },
                            placeholder = stringResource(R.string.search)
                        )
                        Text(
                            modifier = Modifier
                                .clickable {
                                    searchQuery = ""
                                    isSearching = false
                                    cancelSearch()
                                    onCancelSeacrh()
                                }
                                .padding(start = 12.dp, bottom = 12.dp),
                            text = stringResource(R.string.cancel),
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    enabled: Boolean = true,
    onDelete: () -> Unit,
    onFocus: (() -> Unit)? = null
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        modifier = modifier.then(
            Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(MaterialTheme.colors.primaryVariant)
                .onFocusChanged {
                    if (it.isFocused)
                        onFocus?.let { it() }
                }
        ),
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        textStyle = MaterialTheme.typography.body2.copy(
            color = MaterialTheme.colors.primary,
            lineHeight = TextUnit.Unspecified
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() }
        ),
        decorationBox = { innerTextField ->
            CustomSearchBox(
                value = value,
                placeHolder = placeholder,
                iconTint = Grey200,
                onDelete = onDelete,
                padding = 4.dp
            ) {
                innerTextField()
            }
        },
        singleLine = true
    )
}

@Composable
fun CustomSearchBox(
    value: String,
    placeHolder: String,
    iconTint: Color,
    onDelete: () -> Unit,
    padding: Dp,
    innerTextField: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.padding(top = padding, bottom = padding, end = padding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.horizontal_margin)))
            Box {
                Icon(
                    modifier = Modifier.size(21.dp),
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    tint = iconTint
                )
            }

            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacer)))

            Box {
                if (value.isEmpty()) {
                    Text(
                        text = placeHolder,
                        style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.primary)
                    )
                }
                innerTextField()
            }
        }

        if (value.isNotEmpty()) {
            IconButton(
                modifier = Modifier
                    .size(21.dp),
                onClick = onDelete
            ) {
                Icon(imageVector = Icons.Rounded.Close, contentDescription = null, tint = Grey200)
            }
        }
    }
}

@Composable
fun SearchIcon(color: Color = MaterialTheme.colors.primary, onClick: () -> Unit) {
    IconButton(modifier = Modifier.testTag(searchIconTag), onClick = onClick) {
        Icon(imageVector = Icons.Rounded.Search, contentDescription = null, tint = color)
    }
}
