package com.samkt.rickyandmorty.screens.navigation

sealed class Screens(val route: String) {
    object HomeScreen : Screens(route = "home_screen")
    object CharacterScreen : Screens(route = "character_screen")
}
