package com.samkt.rickyandmorty.screens.homeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.samkt.rickyandmorty.domain.model.CharacterInfo
import com.samkt.rickyandmorty.screens.homeScreen.components.CharacterCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
) {
    val characters: LazyPagingItems<CharacterInfo> = viewModel.characters.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Characters") },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            ListCharacters(items = characters)
        }
    }
}

@Composable
fun ListCharacters(items: LazyPagingItems<CharacterInfo>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        content = {
            items(
                count = items.itemCount,
                key = { index ->
                    index
                },
            ) { index ->
                val item = items[index]
                item?.let {
                    CharacterCard(characterInfo = it)
                }
            }
        },
    )
}
