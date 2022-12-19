package com.example.mylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mylist.navigation.setupNavGraph
import com.example.mylist.ui.theme.MylistTheme

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
//    private val mainListViewModel: MainListViewModel =
//        ViewModelProvider(owner = this,
//            factory = MainListViewModel.factory)[MainListViewModel::class.java]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MylistTheme {
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