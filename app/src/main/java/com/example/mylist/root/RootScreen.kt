package com.example.mylist.root

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mylist.mainlist.MainListViewModel
import com.example.mylist.mainlist.MainListViewModelFactory
import com.example.mylist.models.ListItemData
import com.example.mylist.views.MainListView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootScreen(
    application: Application,
    navController: NavController,
    lifecycle: Lifecycle,
) {
    var promptAddNewItemUi by remember { mutableStateOf(false) }
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
            if (!promptAddNewItemUi) {
                FloatingActionButton(onClick = { promptAddNewItemUi = true }) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                }
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Remember Board",
                        color = Color(red = 255, green = 0, blue = 66)
                    )
                },
            )
        },
        bottomBar = {
            if (promptAddNewItemUi) {
                BottomAppBar(containerColor = Color.Green) {
                    // TODO: close this prompt if back if pressed.
                    AddNewItemUi(viewModel = viewModel, finishedAddingItem = {
                        promptAddNewItemUi = false
                    })
                }
            }
        }
    ) { paddingValues ->
        MainListView(
            modifier = Modifier.padding(paddingValues),
            viewModel = viewModel,
            navController = navController,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewItemUi(viewModel: MainListViewModel, finishedAddingItem: () -> Unit) {
    var textFieldValue by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    Row() {
        TextField(
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
            },
            Modifier
                .weight(1f)
                .background(Color.Green)
                .padding(
                    paddingValues = PaddingValues(
                        start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp
                    )
                ),
            placeholder = { Text(text = "enter new item") },
            trailingIcon = {
                IconButton(onClick = {
                    viewModel.addItem(ListItemData(title = textFieldValue))
                    finishedAddingItem()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = "Add a task to the list",
                    )
                }
            })
    }
}
//@Composable
//@Preview(showBackground = true)
//fun RootScreenPreview() {
//    RootScreen(navController = rememberNavController())
//}