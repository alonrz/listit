package com.example.mylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mylist.presentation.navigation.SetupNavGraph
import com.example.mylist.ui.theme.ListItTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ListItTheme {
                navController = rememberNavController()
                SetupNavGraph(
                    navController = navController,
                )
            }
        }
    }
}
