package com.example.listit.presentation.root

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.listit.domain.model.ListItem
import com.example.listit.presentation.components.AddItemBottomSheet
import com.example.listit.presentation.components.EditItemBottomSheet
import com.example.listit.presentation.home.HomeView
import com.example.listit.presentation.home.HomeViewModel
import com.example.listit.presentation.navigation.ScreenNavigation
import com.example.listit.ui.theme.ListItTheme

/**
 * The root scaffold screen containing the top app bar, FAB for adding groups,
 * bottom bar for group creation input, and the home view with group cards.
 * Also manages the add-item and edit-item bottom sheet overlays.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootScreen(
    navController: NavController,
) {
    var promptAddNewItemUi by remember { mutableStateOf(false) }
    var addItemToGroupId by remember { mutableStateOf<String?>(null) }
    var editingItem by remember { mutableStateOf<ListItem?>(null) }
    val viewModel: HomeViewModel = hiltViewModel()
    val groupCards by viewModel.groupCards.collectAsStateWithLifecycle()

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
                    AddNewGroupUi(onAddGroup = { name ->
                        viewModel.addGroup(name)
                    }, finishedAdding = {
                        promptAddNewItemUi = false
                    })
                }
            }
        }
    ) { innerPaddingValues ->
        HomeView(
            modifier = Modifier.padding(innerPaddingValues),
            viewModel = viewModel,
            onAddItemClick = { groupId -> addItemToGroupId = groupId },
            onViewAllClick = { groupId ->
                navController.navigate(ScreenNavigation.ViewAll.createRoute(groupId))
            },
            onItemClick = { item -> editingItem = item },
        )
    }

    // Add Item Bottom Sheet
    addItemToGroupId?.let { groupId ->
        AddItemBottomSheet(
            groups = groupCards,
            initialGroupId = groupId,
            onDismiss = { addItemToGroupId = null },
            onSave = { title, selectedGroupId ->
                viewModel.addItem(title = title, listId = selectedGroupId)
                addItemToGroupId = null
            },
        )
    }

    // Edit Item Bottom Sheet
    editingItem?.let { item ->
        EditItemBottomSheet(
            item = item,
            groups = groupCards,
            onDismiss = { editingItem = null },
            onSave = { id, title, groupId, isDone ->
                viewModel.updateItem(id, title, groupId, isDone)
                editingItem = null
            },
        )
    }
}

/**
 * A bottom bar input field for creating a new group. Shows a text field with a send button;
 * pressing Done on the keyboard or tapping send creates the group and dismisses the UI.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewGroupUi(onAddGroup: (String) -> Unit, finishedAdding: () -> Unit) {
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
            placeholder = { Text(text = "enter new group name") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                if (textFieldValue.isNotEmpty()) {
                    onAddGroup(textFieldValue)
                    finishedAdding()
                }
            }),
            trailingIcon = {
                IconButton(enabled = textFieldValue.isNotEmpty(),
                    onClick = {
                        onAddGroup(textFieldValue)
                        finishedAdding()
                    }) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = "Add a new group",
                    )
                }
            })
        LaunchedEffect(key1 = Unit, block = { focusRequester.requestFocus() })
    }
}

/**
 * A utility composable that intercepts the system back press when enabled,
 * invoking the provided callback instead of default back navigation.
 */
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

@Composable
@PreviewLightDark
private fun AddNewGroupUiPreview() {
    ListItTheme {
        AddNewGroupUi(
            onAddGroup = {},
            finishedAdding = {},
        )
    }
}
