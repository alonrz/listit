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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listit.presentation.home.GroupCardUiState
import com.example.listit.ui.theme.ListItTheme

/**
 * A modal bottom sheet for creating a new task. Contains a text field for the task title,
 * horizontally scrollable group chips for selecting which list to add to, and a save button.
 * Pressing the keyboard Done action also triggers save.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemBottomSheet(
    groups: List<GroupCardUiState>,
    initialGroupId: String,
    onDismiss: () -> Unit,
    onSave: (title: String, groupId: String) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    ) {
        AddItemSheetContent(
            groups = groups,
            initialGroupId = initialGroupId,
            onSave = onSave,
            requestFocus = true,
        )
    }
}

/**
 * The inner content of the add-item sheet: title input, group selector chips,
 * and a save button. Extracted for preview support.
 */
@Composable
internal fun AddItemSheetContent(
    groups: List<GroupCardUiState>,
    initialGroupId: String,
    onSave: (title: String, groupId: String) -> Unit,
    requestFocus: Boolean = false,
) {
    var taskTitle by remember { mutableStateOf("") }
    var selectedGroupId by remember { mutableStateOf(initialGroupId) }
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
            text = "New Task",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
            ),
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Subtitle
        Text(
            text = "What's on your mind?",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Text field
        OutlinedTextField(
            value = taskTitle,
            onValueChange = { taskTitle = it },
            placeholder = {
                Text(
                    text = "I want to...",
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
                    onSave(taskTitle.trim(), selectedGroupId)
                }
            }),
        )

        Spacer(modifier = Modifier.height(24.dp))

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
                    onSave(taskTitle.trim(), selectedGroupId)
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
                text = "Save Task",
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
private fun AddItemSheetContentPreview() {
    ListItTheme {
        Surface(color = MaterialTheme.colorScheme.surfaceContainer) {
            AddItemSheetContent(
                groups = previewGroups,
                initialGroupId = "1",
                onSave = { _, _ -> },
            )
        }
    }
}
