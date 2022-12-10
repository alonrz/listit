package com.example.mylist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mylist.edit.EditScreen
import com.example.mylist.root.RootScreen

@Composable
fun setupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = ScreenNavigation.Root.route,
    ) {
        composable(
            route = ScreenNavigation.Root.route
        ) {
            RootScreen(navController = navController)
        }
        composable(
            route = ScreenNavigation.Edit.route
        ) {
            EditScreen(navController = navController)//navigation pass id, name, etc
        }
    }
}