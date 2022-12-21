package com.tapdeveloper.themoviedb.presentation.moviesList.composables

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.tapdeveloper.themoviedb.ui.theme.Grey200
import com.tapdeveloper.themoviedb.ui.theme.Grey300
import com.tapdeveloper.themoviedb.ui.theme.White

@Composable
fun TopSearchBar(
    title: String,
    modifier: Modifier = Modifier,
    isSearching: Boolean,
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    cancelSearch: () -> Unit,
    onClickSearchIcon: () -> Unit,
    onDeleteSearch: () -> Unit
) {
    TopAppBar(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!isSearching) {
                    SearchIcon(onClickSearchIcon, White)
                    Text(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        text = title,
                        color = White
                    )
                } else {
                    SearchBox(
                        modifier = Modifier
                            .padding(start = 16.dp, bottom = 12.dp)
                            .fillMaxWidth(0.8f),
                        value = searchQuery,
                        onValueChange = onQueryChange,
                        onDelete = onDeleteSearch,
                        placeholder = "Buscar"
                    )
                    Text(
                        modifier = Modifier
                            .clickable { cancelSearch() }
                            .padding(start = 12.dp, bottom = 12.dp),
                        text = "Cancel",
                        color = White
                    )
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
        modifier = modifier.then( // porqque then?
            Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(Grey300)
                .onFocusChanged {
                    if (it.isFocused)
                        onFocus?.let { it() }
                }),
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        textStyle = MaterialTheme.typography.body2.copy(
            color = White,
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
    innerTextField: @Composable() () -> Unit
) {
    Row(
        modifier = Modifier.padding(top = padding, bottom = padding, end = padding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Box {
                Icon(
                    modifier = Modifier.size(21.dp),
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    tint = iconTint
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Box {
                if (value.isEmpty()) {
                    Text(
                        text = placeHolder,
                        style = MaterialTheme.typography.body2.copy(color = White)
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
fun SearchIcon(onClick: () -> Unit, color: Color) {
    IconButton(onClick = onClick) {
        Icon(imageVector = Icons.Rounded.Search, contentDescription = null, tint = color)
    }
}