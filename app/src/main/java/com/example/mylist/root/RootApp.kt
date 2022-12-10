package com.example.mylist.root

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mylist.mainlist.MainListViewModel
import com.example.mylist.views.MainListView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootScreen(
    navController: NavController,
) {
    val viewModel: MainListViewModel = viewModel(factory = MainListViewModel.Factory)
    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.addItem()
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
            }
        }
    ) {
        MainListView(items = viewModel.items, navController = navController) { id, checked ->
            viewModel.changeItemCheckStatus(
                id,
                checked = checked,
            )
        }
    }
}

//@Composable
//@Preview(showBackground = true)
//fun RootScreenPreview() {
//    RootScreen(navController = rememberNavController())
//}