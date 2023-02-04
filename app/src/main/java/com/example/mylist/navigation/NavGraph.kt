package com.example.mylist.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mylist.edit.EditScreen
import com.example.mylist.root.RootScreen

@Composable
fun setupNavGraph(
    application: Application,
    navController: NavHostController,
    lifecycle: Lifecycle,

    ) {
    NavHost(
        navController = navController,
        startDestination = ScreenNavigation.Root.route,
    ) {
        composable(
            route = ScreenNavigation.Root.route
        ) {
            RootScreen(
                application = application,
                navController = navController,
                lifecycle = lifecycle,
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
                lifecycle = lifecycle,
            )
        }
    }
}