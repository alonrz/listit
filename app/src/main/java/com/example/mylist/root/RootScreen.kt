package com.example.mylist.root

import android.app.Application
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mylist.mainlist.MainListView
import com.example.mylist.mainlist.MainListViewModel
import com.example.mylist.mainlist.MainListViewModelFactory
import com.example.mylist.models.ListItemData
import com.example.mylist.navigation.ScreenNavigation

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
    BackPressedHandler(
        onBackPressed = { promptAddNewItemUi = false },
        isEnabled = promptAddNewItemUi
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
                    )
                },
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                            )
                        }
                    } else null
                },
                actions = {
                    IconButton(onClick = { navController.navigate(route = ScreenNavigation.Settings.route) }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Open Settings",
                            modifier = Modifier.padding(PaddingValues(end = 12.dp))
                        )
                    }
                }
            )
        },
        bottomBar = {
            if (promptAddNewItemUi) {
                BottomAppBar() {
                    // TODO: close this prompt if back if pressed.
                    AddNewItemUi(viewModel = viewModel, finishedAddingItem = {
                        promptAddNewItemUi = false
                    })
                }
            }
        }
    ) { innerPaddingValues ->
        MainListView(
            modifier = Modifier.padding(innerPaddingValues),
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
    val focusRequester = remember { FocusRequester() }
    Row {
        TextField(
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
            },
            Modifier
                .weight(1f)
                .focusRequester(focusRequester)
                .padding(
                    paddingValues = PaddingValues(
                        start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp
                    )
                ),
            placeholder = { Text(text = "enter new item") },
            trailingIcon = {
                IconButton(enabled = textFieldValue.isNotEmpty(),
                    onClick = {
                        viewModel.addItem(ListItemData(title = textFieldValue))
                        finishedAddingItem()
                    }) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = "Add a task to the list",
                    )
                }
            })
        LaunchedEffect(key1 = Unit, block = { focusRequester.requestFocus() })
    }
}

@Composable
fun BackPressedHandler(
    backPressedDispatcher: OnBackPressedDispatcher? = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    isEnabled: Boolean,
    onBackPressed: () -> Unit,
) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)

    val backCallBack = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    SideEffect {
        backCallBack.isEnabled = isEnabled
    }
    DisposableEffect(key1 = backPressedDispatcher) {
        backPressedDispatcher?.addCallback(backCallBack)

        onDispose {
            backCallBack.remove()
        }
    }
}
//@Composable
//@Preview(showBackground = true)
//fun RootScreenPreview() {
//    RootScreen(navController = rememberNavController())
//}