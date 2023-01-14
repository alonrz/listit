package com.example.mylist.edit

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
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
    navController: NavController,
    lifecycle: Lifecycle,
) {
    val viewModel: EditViewModel = viewModel(
        factory = EditViewModelFactory(
            itemId = itemId,
            itemTitle = itemTitle,
            application = Application(),
            lifecycle = lifecycle,
        )
    )
    val textFieldValue = viewModel.itemTitle
    val focusRequester = remember { FocusRequester() }
    var openDeleteDialog by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues = PaddingValues(all = 16.dp)),
    ) {
        TextField(
            value = textFieldValue.value,
            onValueChange = {
                kotlin.synchronized(this) {
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

@Composable
@Preview(showBackground = true)
fun EditScreenPreview() {
//    EditScreen(navController = rememberNavController())
}