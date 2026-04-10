package com.example.listit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.listit.domain.model.ThemeMode
import com.example.listit.presentation.MainViewModel
import com.example.listit.presentation.navigation.SetupNavGraph
import com.example.listit.ui.theme.ListItTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val themeState by viewModel.themeState.collectAsStateWithLifecycle()
            val darkTheme = when (themeState.themeMode) {
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
            }

            ListItTheme(
                darkTheme = darkTheme,
                dynamicColor = themeState.useDynamicColor,
            ) {
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
