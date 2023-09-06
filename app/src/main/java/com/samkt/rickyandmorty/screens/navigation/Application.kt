package com.samkt.rickyandmorty.screens.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.samkt.rickyandmorty.RickyAndMortyApplication
import com.samkt.rickyandmorty.di.viewModelFactory
import com.samkt.rickyandmorty.screens.homeScreen.HomeScreen
import com.samkt.rickyandmorty.screens.homeScreen.HomeScreenViewModel
import com.samkt.rickyandmorty.screens.location.LocationScreen
import com.samkt.rickyandmorty.screens.location.LocationViewModel
import com.samkt.rickyandmorty.screens.searchScreen.SearchScreen
import com.samkt.rickyandmorty.screens.searchScreen.SearchScreenViewModel
import com.samkt.rickyandmorty.screens.singleCharacterScreen.SingleCharacterScreen
import com.samkt.rickyandmorty.screens.singleCharacterScreen.SingleCharacterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationHomeScreen(
    onSearchClicked: () -> Unit
) {
    val navController = rememberNavController()

    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }
    Scaffold(
        bottomBar = {
            NavigationBar {
                navigationItems.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = index == navigationSelectedItem,
                        onClick = {
                            navigationSelectedItem = index
                            navController.navigate(navItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = null,
                            )
                        },
                        label = {
                            Text(text = navItem.label)
                        },
                    )
                }
            }
        },
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = "characters",
        ) {
            destination("characters") {
                CharacterScreen(
                    onSearchClicked = onSearchClicked
                )
            }
            destination("location") {
                val viewModelFactory = viewModelFactory {
                    LocationViewModel(RickyAndMortyApplication.app.locationRepository())
                }
                val locationViewModel = viewModel<LocationViewModel>(factory = viewModelFactory)
                LocationScreen(viewModel = locationViewModel)
            }
        }
    }
}

@Composable
fun CharacterScreen(
    onSearchClicked:()->Unit
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.CharacterScreen.route) {
        destination(route = Screens.CharacterScreen.route) {
            val homeViewModel: HomeScreenViewModel = viewModel(
                factory = viewModelFactory {
                    HomeScreenViewModel(RickyAndMortyApplication.app.charactersRepository())
                },
            )
            HomeScreen(
                viewModel = homeViewModel,
                onCharacterClick = { id ->
                    navController.navigate(Screens.CharacterScreen.route + "?id=$id")
                },
                onSearchClicked = onSearchClicked
            )
        }
        destination(
            route = Screens.CharacterScreen.route + "?id={id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                },
            ),
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt("id") ?: 1
            val characterScreenViewModel =
                viewModel<SingleCharacterViewModel>(
                    factory = viewModelFactory {
                        SingleCharacterViewModel(RickyAndMortyApplication.app.charactersRepository())
                    },
                )
            SingleCharacterScreen(
                viewModel = characterScreenViewModel,
                id = id,
                navController = navController,
            )
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route,
    ) {
        composable(Screens.HomeScreen.route) {
            ApplicationHomeScreen(
                onSearchClicked = {
                    navController.navigate(Screens.SearchScreen.route)
                }
            )
        }
        verticalDestination(Screens.SearchScreen.route) {
            val searchScreenViewModel =
                viewModel<SearchScreenViewModel>(
                    factory = viewModelFactory {
                        SearchScreenViewModel(RickyAndMortyApplication.app.charactersRepository())
                    },
                )
            SearchScreen(viewModel = searchScreenViewModel)
        }
    }
}

fun NavGraphBuilder.destination(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = route,
        content = content,
        arguments = arguments,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                animationSpec = tween(700),
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                animationSpec = tween(700),
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                animationSpec = tween(700),
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                animationSpec = tween(700),
            )
        },
    )
}

fun NavGraphBuilder.verticalDestination(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    val duration = 700
    composable(
        route = route,
        arguments = arguments,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Up,
                animationSpec = tween(duration),
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Up,
                animationSpec = tween(duration),
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Down,
                animationSpec = tween(duration),
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Down,
                animationSpec = tween(duration),
            )
        },
        content = content,
    )
}
