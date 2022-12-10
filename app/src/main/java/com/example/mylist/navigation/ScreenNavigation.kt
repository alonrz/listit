package com.example.mylist.navigation

sealed class ScreenNavigation(val route: String) {
    object Root : ScreenNavigation(route = "root_screen")
    object Edit : ScreenNavigation(route = "edit_screen")
}
