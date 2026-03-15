package com.example.mylist.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mylist.presentation.edit.EditScreen
import com.example.mylist.presentation.root.RootScreen
import com.example.mylist.presentation.settings.SettingsScreen

@Composable
fun setupNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = ScreenNavigation.Root.route,
    ) {
        composable(
            route = ScreenNavigation.Root.route
        ) {
            RootScreen(
                navController = navController,
            )
        }
        composable(
            route = ScreenNavigation.Edit.route,
            arguments = listOf(
                navArgument(name = EDIT_ARGUMENT_KEY) { type = NavType.StringType },
                navArgument(name = EDIT_ARGUMENT_KEY2) { type = NavType.StringType },
                navArgument(name = EDIT_ARGUMENT_KEY3) { type = NavType.BoolType },
            )
        ) {
            EditScreen(
                navController = navController,
                itemId = it.arguments?.getString(EDIT_ARGUMENT_KEY) ?: "",
                itemTitle = it.arguments?.getString(EDIT_ARGUMENT_KEY2) ?: "",
                isDone = it.arguments?.getBoolean(EDIT_ARGUMENT_KEY3) ?: false,
            )
        }
        composable(
            route = ScreenNavigation.Settings.route
        ) {
            SettingsScreen(

            )
        }
    }
}
