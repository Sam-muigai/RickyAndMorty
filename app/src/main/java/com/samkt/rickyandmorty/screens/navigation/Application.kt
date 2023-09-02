package com.samkt.rickyandmorty.screens.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.samkt.rickyandmorty.RickyAndMortyApplication
import com.samkt.rickyandmorty.di.viewModelFactory
import com.samkt.rickyandmorty.screens.homeScreen.HomeScreen
import com.samkt.rickyandmorty.screens.homeScreen.HomeScreenViewModel
import com.samkt.rickyandmorty.screens.singleCharacterScreen.SingleCharacterScreen
import com.samkt.rickyandmorty.screens.singleCharacterScreen.SingleCharacterViewModel

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route,
    ) {
        composable(
            route = Screens.HomeScreen.route,
        ) {
            val homeViewModel: HomeScreenViewModel = viewModel(
                factory = viewModelFactory {
                    HomeScreenViewModel(RickyAndMortyApplication.app.charactersRepository())
                },
            )
            HomeScreen(
                viewModel = homeViewModel,
                navController = navController,
            )
        }
        composable(
            route = Screens.CharacterScreen.route + "?id={id}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                },
            ),
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt("id") ?: 1
            val singleCharacterViewModel: SingleCharacterViewModel =
                viewModel(
                    factory = viewModelFactory {
                        SingleCharacterViewModel(RickyAndMortyApplication.app.singleCharacterRepository())
                    },
                )
            SingleCharacterScreen(
                viewModel = singleCharacterViewModel,
                id = id,
                navController = navController,
            )
        }
    }
}
