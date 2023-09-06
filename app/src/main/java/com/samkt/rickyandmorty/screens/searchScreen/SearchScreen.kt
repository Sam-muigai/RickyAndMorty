package com.samkt.rickyandmorty.screens.searchScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.samkt.rickyandmorty.screens.homeScreen.components.CharacterCard

@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel,
) {
    val state = viewModel.searchScreenState.collectAsState().value
    SearchScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenContent(
    modifier: Modifier = Modifier,
    state: SearchScreenState,
    onEvent: (SearchScreenEvents) -> Unit,
) {
    Scaffold(modifier = modifier) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                value = state.searchTerm,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                    )
                },
                onValueChange = { onEvent(SearchScreenEvents.OnSearchTermChange(it)) },
                trailingIcon = {
                    IconButton(onClick = { onEvent.invoke(SearchScreenEvents.OnClearClicked) }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear",
                        )
                    }
                },

            )
            Spacer(modifier = Modifier.height(4.dp))
            AnimatedVisibility(visible = state.isLoading) {
                CircularProgressIndicator()
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                content = {
                    items(
                        items = state.characters,
                    ) {
                        CharacterCard(
                            characterInfo = it,
                            onCharacteClick = {},
                        )
                    }
                },
            )
        }
    }
}
