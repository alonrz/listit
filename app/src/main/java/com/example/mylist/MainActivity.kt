package com.example.mylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mylist.repo.MainListRepo
import com.example.mylist.repo.MainListRepoLocalData
import com.example.mylist.ui.theme.MylistTheme
import com.example.mylist.views.MainListView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MylistTheme {
                // A surface container using the 'background' color from the theme
                Surface(

                    color = MaterialTheme.colorScheme.background
                ) {
                    RootApp()
                }
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
        MainListView(items = viewModel.items)
    }


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MylistTheme {
        RootApp()
    }
}