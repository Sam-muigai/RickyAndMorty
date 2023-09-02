package com.samkt.rickyandmorty.screens.singleCharacterScreen

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.samkt.rickyandmorty.domain.model.CharacterInfo
import com.samkt.rickyandmorty.screens.homeScreen.LoadingAnimation

@Composable
fun SingleCharacterScreen(
    viewModel: SingleCharacterViewModel,
    id: Int,
    navController: NavHostController,
) {
    val state = viewModel.singleCharacterScreenStates.collectAsState().value
    val context = LocalContext.current
    LaunchedEffect(
        key1 = true,
        block = {
            viewModel.getCharacter(id)
        },
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (state.loading) {
            LoadingAnimation(modifier = Modifier.size(100.dp))
        }
        state.character?.let {
            CharacterScreenContent(
                character = it,
                context = context,
            )
        }
        state.errorMessage?.let {
            Text(text = it)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreenContent(
    modifier: Modifier = Modifier,
    character: CharacterInfo,
    context: Context,
) {
    Scaffold(modifier = modifier) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(character.image)
                    .build(),
                contentDescription = character.name,
                modifier = Modifier.fillMaxWidth().height(270.dp),
                contentScale = ContentScale.FillBounds,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Name: ${character.name}")
            Text(text = "Status: ${character.status}")
        }
    }
}
