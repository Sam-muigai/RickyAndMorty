package com.samkt.rickyandmorty.screens.navigation

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val label: String,
    val route: String,
    val icon: ImageVector,
)

val navigationItems = listOf(
    BottomNavItem(
        "Characters",
        "characters",
        Icons.Default.Person,
    ),
    BottomNavItem(
        "Location",
        "location",
        Icons.Default.LocationOn,
    ),
)
