package com.example.listit

import android.os.Bundle
import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.listit.presentation.navigation.SetupNavGraph
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.listit.ui.theme.ListItTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ListItTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    navController = rememberNavController()
                    SetupNavGraph(
                        navController = navController,
                    )
                }
            }
        }
    }
}
