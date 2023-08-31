package com.samkt.rickyandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samkt.rickyandmorty.di.viewModelFactory
import com.samkt.rickyandmorty.screens.homeScreen.HomeScreen
import com.samkt.rickyandmorty.screens.homeScreen.HomeScreenViewModel
import com.samkt.rickyandmorty.ui.theme.RickyAndMortyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickyAndMortyTheme {
                val viewModel = viewModel<HomeScreenViewModel>(
                    factory = viewModelFactory {
                        HomeScreenViewModel(RickyAndMortyApplication.app.provideRickyAndMortyRepository())
                    },
                )
                HomeScreen(viewModel = viewModel)
            }
        }
    }
}
