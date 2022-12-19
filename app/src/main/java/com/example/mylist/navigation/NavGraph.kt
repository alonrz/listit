package com.example.mylist.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
            route = ScreenNavigation.Edit.route
        ) {
            EditScreen(navController = navController)//navigation pass id, name, etc
        }
    }
}