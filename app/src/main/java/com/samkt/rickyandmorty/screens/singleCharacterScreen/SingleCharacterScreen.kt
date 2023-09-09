package com.samkt.rickyandmorty.screens.singleCharacterScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.samkt.rickyandmorty.domain.model.CharacterInfo
import com.samkt.rickyandmorty.screens.homeScreen.LoadingAnimation
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun SingleCharacterScreen(
    viewModel: SingleCharacterViewModel,
    id: Int,
    navController: NavHostController,
) {
    val state = viewModel.singleCharacterScreenStates.collectAsState().value
    LaunchedEffect(
        key1 = true,
        block = {
            viewModel.getCharacter(id)
        },
    )
    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        if (state.loading) {
            LoadingAnimation(modifier = Modifier.size(100.dp))
        }
        state.character?.let {
            CharacterScreenContent(
                character = it,
                onBackClicked = {
                    navController.popBackStack()
                },

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
    onBackClicked: () -> Unit,
) {
    Scaffold(modifier = modifier) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                CoilImage(
                    modifier = Modifier.fillMaxWidth()
                        .height(270.dp)
                        .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
                        .blur(15.dp),
                    imageModel = character.image,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                )
                CoilImage(
                    modifier = Modifier.size(150.dp).clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
                    imageModel = character.image,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    circularReveal = CircularReveal(duration = 1000),
                )
                FloatingActionButton(
                    modifier = Modifier.size(50.dp).align(Alignment.TopStart).padding(8.dp),
                    onClick = onBackClicked,
                    shape = CircleShape,
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Name: ${character.name}")
            Text(text = "Status: ${character.status}")
        }
    }
}
