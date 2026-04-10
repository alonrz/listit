package com.example.listit.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listit.domain.model.ListItem
import com.example.listit.presentation.home.GroupCardUiState
import com.example.listit.ui.theme.ListItTheme

/**
 * A modal bottom sheet for editing an existing task. Pre-populated with the item's current title,
 * completion status checkbox, and group selector chips. Allows changing the title, toggling
 * done/not-done, and moving the item to a different group.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditItemBottomSheet(
    item: ListItem,
    groups: List<GroupCardUiState>,
    onDismiss: () -> Unit,
    onSave: (id: String, title: String, groupId: String, isDone: Boolean) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    ) {
        EditItemSheetContent(
            item = item,
            groups = groups,
            onSave = onSave,
            requestFocus = true,
        )
    }
}

/**
 * The inner content of the edit-item sheet: pre-filled title input, done checkbox,
 * group selector chips, and a save button. Extracted for preview support.
 */
@Composable
internal fun EditItemSheetContent(
    item: ListItem,
    groups: List<GroupCardUiState>,
    onSave: (id: String, title: String, groupId: String, isDone: Boolean) -> Unit,
    requestFocus: Boolean = false,
) {
    var taskTitle by remember { mutableStateOf(item.title) }
    var selectedGroupId by remember { mutableStateOf(item.listId) }
    var isDone by remember { mutableStateOf(item.isDone) }
    val focusRequester = remember { FocusRequester() }

    if (requestFocus) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp),
    ) {
        // Title
        Text(
            text = "Edit Task",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
            ),
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Text field
        OutlinedTextField(
            value = taskTitle,
            onValueChange = { taskTitle = it },
            placeholder = {
                Text(
                    text = "Task name...",
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ListCardColor.BLUE.color,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                if (taskTitle.isNotBlank()) {
                    onSave(item.id, taskTitle.trim(), selectedGroupId, isDone)
                }
            }),
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Done status checkbox
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = isDone,
                onCheckedChange = { isDone = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = ListCardColor.BLUE.color,
                ),
            )
            Text(
                text = if (isDone) "Completed" else "Not completed",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Select list label
        Text(
            text = "SELECT LIST",
            style = MaterialTheme.typography.labelMedium.copy(
                letterSpacing = 1.sp,
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Horizontally scrollable group chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            groups.forEach { group ->
                val isSelected = group.groupId == selectedGroupId
                GroupChip(
                    name = group.groupName,
                    icon = group.style.icon,
                    accentColor = group.style.accentColor.color,
                    isSelected = isSelected,
                    onClick = { selectedGroupId = group.groupId },
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Save button
        Button(
            onClick = {
                if (taskTitle.isNotBlank()) {
                    onSave(item.id, taskTitle.trim(), selectedGroupId, isDone)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ListCardColor.BLUE.color,
            ),
            enabled = taskTitle.isNotBlank(),
        ) {
            Text(
                text = "Save Changes",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
                color = Color.White,
            )
        }
    }
}

private val previewGroups = listOf(
    GroupCardUiState(
        groupId = "1",
        groupName = "Work",
        items = emptyList(),
        style = ListCardStyle(icon = ListCardIcon.WORK, accentColor = ListCardColor.BLUE),
    ),
    GroupCardUiState(
        groupId = "2",
        groupName = "Shopping",
        items = emptyList(),
        style = ListCardStyle(icon = ListCardIcon.SHOPPING, accentColor = ListCardColor.GREEN),
    ),
)

@Composable
@PreviewLightDark
private fun EditItemSheetContentPreview() {
    ListItTheme {
        Surface(color = MaterialTheme.colorScheme.surfaceContainer) {
            EditItemSheetContent(
                item = ListItem(id = "1", title = "Buy groceries", isDone = false, listId = "2"),
                groups = previewGroups,
                onSave = { _, _, _, _ -> },
            )
        }
    }
}
