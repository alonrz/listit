package com.example.mylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mylist.navigation.setupNavGraph
import com.example.mylist.ui.theme.ListItTheme

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ListItTheme {
                navController = rememberNavController()
                setupNavGraph(
                    application = application,
                    navController = navController,
                    lifecycle = lifecycle,
                )
            }
        }
    }
}