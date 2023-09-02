package com.samkt.rickyandmorty.screens.homeScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.samkt.rickyandmorty.R
import com.samkt.rickyandmorty.domain.model.CharacterInfo
import com.samkt.rickyandmorty.screens.homeScreen.components.CharacterCard
import com.samkt.rickyandmorty.screens.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    navController: NavHostController
) {
    val characters = viewModel.characters.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Characters") },
                actions = {
                    IconButton(
                        onClick = {
                            // TODO: Add navigation to search screen
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            when (characters.loadState.refresh) {
                is LoadState.Error -> Unit
                is LoadState.Loading -> {
                    LoadingAnimation(modifier = Modifier.size(100.dp))
                }

                is LoadState.NotLoading -> {
                    val loadMoreItems = characters.loadState.append == LoadState.Loading
                    ListCharacters(
                        characters = characters,
                        loadingNextItem = loadMoreItems,
                        onCharacterClick = {id ->
                            navController.navigate(Screens.CharacterScreen.route + "?id=$id")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ListCharacters(
    characters: LazyPagingItems<CharacterInfo>,
    loadingNextItem: Boolean,
    onCharacterClick: (Int) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        content = {
            items(
                count = characters.itemCount,
                key = { index ->
                    index
                },
            ) { index ->
                val item = characters[index]
                item?.let {
                    CharacterCard(
                        characterInfo = it,
                        onCharacteClick = onCharacterClick,
                    )
                }
            }
            if (loadingNextItem) {
                item(span = { GridItemSpan(3) }) {
                    CircularProgressIndicator(strokeWidth = 2.dp)
                }
            }
        },
    )
}

@Composable
fun LoadingAnimation(
    modifier: Modifier,
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading))
    LottieAnimation(
        modifier = modifier,
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )
}
