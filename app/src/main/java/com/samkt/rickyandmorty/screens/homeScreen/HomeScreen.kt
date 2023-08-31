package com.samkt.rickyandmorty.screens.homeScreen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.samkt.rickyandmorty.domain.model.CharacterInfo

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
) {
    val characters: LazyPagingItems<CharacterInfo> = viewModel.characters.collectAsLazyPagingItems()
    ListCharacters(items = characters)
}

@Composable
fun ListCharacters(items: LazyPagingItems<CharacterInfo>) {
    LazyColumn(
        content = {
            items(items = items, key = { it.id }) {
                Text(text = it?.name ?: "No name")
            }
        },
    )
}
