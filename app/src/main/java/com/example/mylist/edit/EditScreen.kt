package com.example.mylist.edit

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mylist.navigation.ScreenNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    modifier: Modifier = Modifier.background(Color.Blue),
    itemId: String,
    itemTitle: String,
    isDone: Boolean,
    navController: NavController,
    lifecycle: Lifecycle,
) {
    val viewModel: EditViewModel = viewModel(
        factory = EditViewModelFactory(
            itemId = itemId,
            itemTitle = itemTitle,
            isDone = isDone,
            application = Application(),
            lifecycle = lifecycle,
        )
    )
    val textFieldValue = viewModel.itemTitle
    val focusRequester = remember { FocusRequester() }
    var openDeleteDialog by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (itemTitle.isBlank()) "Item" else
                            itemTitle.let { str ->
                                if (str.length < 15) str
                                else {
                                    val trucatedString = str.substring(
                                        startIndex = 0,
                                        endIndex = 15
                                    )
                                    val index = trucatedString.lastIndexOf(' ')
                                    if (index == 14) str
                                    else if (index > 10) "${
                                        str.substring(
                                            startIndex = 0,
                                            endIndex = index
                                        )
                                    }..."
                                    else "$str..."
                                }
                            },
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
            )
        },
    ) { innerPaddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPaddingValues/*paddingValues = PaddingValues(all = 16.dp)*/),
        ) {
            TextField(
                value = textFieldValue.value,
                onValueChange = {
                    synchronized(this) {
                        textFieldValue.value = it
                        viewModel.changeItemTitle(it)
                    }
                },
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .height(200.dp),
                singleLine = false,
            )
            Text(
                text = viewModel.itemTitle.value,
                color = Color.Cyan,
                lineHeight = 50.sp,
                fontSize = MaterialTheme.typography.displayLarge.fontSize,
                fontWeight = FontWeight.Bold,
            )
            Button(
                onClick = {
                    openDeleteDialog = true
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete item",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = "Delete")
            }

            Text(text = "is done?")
            Text(text = if (viewModel.isDone.value) "Done" else "Not Done")

            if (openDeleteDialog) {
                AlertDialog(onDismissRequest = { openDeleteDialog = false },
                    title = {
                        Text(text = "Delete Item")
                    },
                    text = {
                        Text(text = "Are you sure you want to delete this item?")
                    },
                    confirmButton = {
                        Button(onClick = {
                            viewModel.deleteItem()
                            navController.navigate(route = ScreenNavigation.Root.route)
                        }) {
                            Text("Delete")
                        }

                    },
                    dismissButton = {
                        Button(onClick = { openDeleteDialog = false }) {
                            Text("Cancel")
                        }
                    })
            }
            LaunchedEffect(key1 = Unit, block = { focusRequester.requestFocus() })
        }
    }
}

@Composable
@Preview(showBackground = true)
fun EditScreenPreview() {
//    EditScreen(navController = rememberNavController())
}