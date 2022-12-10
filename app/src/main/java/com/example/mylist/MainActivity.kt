package com.example.mylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mylist.mainlist.MainListViewModel
import com.example.mylist.navigation.setupNavGraph
import com.example.mylist.repo.MainListRepo
import com.example.mylist.repo.MainListRepoLocalData
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
                setupNavGraph(navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootApp() {
    val viewModel = MainListViewModel(MainListRepo(MainListRepoLocalData()))
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
        MainListView(items = viewModel.items) { id, isChecked
            -> viewModel.markItemDone(id) }
    }


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MylistTheme {
        RootApp()
    }
}