package com.example.mylist.root

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mylist.mainlist.MainListViewModel
import com.example.mylist.mainlist.MainListViewModelFactory
import com.example.mylist.views.MainListView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootScreen(
    application: Application,
    navController: NavController,
    lifecycle: Lifecycle,
) {
    val viewModel: MainListViewModel =
        viewModel(
            factory = MainListViewModelFactory(
                application = application,
                lifecycle = lifecycle,
            )
        )
    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.addItem()
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Remember Board")
                },
                modifier = Modifier.background(Color.Cyan)
            )
        }
    ) { paddingValues ->
        MainListView(
            modifier = Modifier.padding(paddingValues),
            viewModel = viewModel,
            navController = navController,
        )
    }
}

//@Composable
//@Preview(showBackground = true)
//fun RootScreenPreview() {
//    RootScreen(navController = rememberNavController())
//}