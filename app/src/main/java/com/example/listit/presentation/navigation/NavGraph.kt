package com.example.listit.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.listit.presentation.edit.EditScreen
import com.example.listit.presentation.root.RootScreen
import com.example.listit.presentation.settings.SettingsScreen
import com.example.listit.presentation.viewall.ViewAllScreen

@Composable
fun SetupNavGraph(
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
            )
        }
        composable(
            route = ScreenNavigation.ViewAll.route,
            arguments = listOf(
                navArgument(name = VIEW_ALL_ARG_GROUP_ID) { type = NavType.StringType },
            )
        ) {
            ViewAllScreen(
                navController = navController,
            )
        }
        composable(
            route = ScreenNavigation.Settings.route
        ) {
            SettingsScreen(
                navController = navController,
            )
        }
    }
}
