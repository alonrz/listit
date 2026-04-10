package com.example.listit.presentation.viewall

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.listit.domain.model.ListItem
import com.example.listit.presentation.components.AddItemBottomSheet
import com.example.listit.presentation.components.EditItemBottomSheet
import com.example.listit.presentation.components.TaskItemRow

/**
 * Full-screen view of all tasks in a single group. Features a large colored header block
 * with the group's accent color, icon, name, settings gear, and a FAB for adding items.
 * Below the header is a scrollable list of all items with check/edit support.
 */
@Composable
fun ViewAllScreen(
    navController: NavController,
    viewModel: ViewAllViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showAddSheet by remember { mutableStateOf(false) }
    var editingItem by remember { mutableStateOf<ListItem?>(null) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddSheet = true },
                containerColor = uiState.style.accentColor.color,
                contentColor = Color.White,
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add item",
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            // Colored header block
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(uiState.style.accentColor.color)
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(bottom = 24.dp),
            ) {
                // Single row: back arrow, group icon + name, settings gear
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp, end = 4.dp, top = 8.dp),
                    verticalAlignment = Alignment.Top,
                ) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                        )
                    }

                    // Group icon + name - expands up to 2 lines
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.Top,
                    ) {
                        Icon(
                            imageVector = uiState.style.icon.imageVector,
                            contentDescription = uiState.style.icon.label,
                            tint = Color.White,
                            modifier = Modifier.size(32.dp),
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = uiState.groupName,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp,
                            ),
                            color = Color.White,
                            maxLines = 2,
                        )
                    }

                    IconButton(onClick = { /* future: group settings */ }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Group settings",
                            tint = Color.White,
                        )
                    }
                }
            }

            // Items list
            if (uiState.items.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 48.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Nothing here yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                ) {
                    items(items = uiState.items, key = { it.id }) { item ->
                        TaskItemRow(
                            item = item,
                            onCheckedClick = { id, isDone ->
                                viewModel.changeItemCheckStatus(id, checked = isDone.not())
                            },
                            onItemClick = { editingItem = it },
                            modifier = Modifier.padding(vertical = 2.dp),
                        )
                    }
                }
            }
        }
    }

    // Add Item Bottom Sheet
    if (showAddSheet) {
        AddItemBottomSheet(
            groups = uiState.allGroups,
            initialGroupId = uiState.groupId,
            onDismiss = { showAddSheet = false },
            onSave = { title, selectedGroupId ->
                viewModel.addItem(title)
                showAddSheet = false
            },
        )
    }

    // Edit Item Bottom Sheet
    editingItem?.let { item ->
        EditItemBottomSheet(
            item = item,
            groups = uiState.allGroups,
            onDismiss = { editingItem = null },
            onSave = { id, title, groupId, isDone ->
                viewModel.updateItem(id, title, groupId, isDone)
                editingItem = null
            },
        )
    }
}
