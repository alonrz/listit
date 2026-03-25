package com.example.listit.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listit.domain.model.ListItem
import com.example.listit.ui.theme.ListItTheme

private const val MAX_PREVIEW_ITEMS = 3

@Composable
fun GroupCardView(
    listName: String,
    items: List<ListItem>,
    style: ListCardStyle,
    onAddItemClick: () -> Unit,
    onViewAllClick: () -> Unit,
    onCheckedClick: (id: String, isDone: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val accentColor = style.accentColor.color

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
    ) {
        Column {
            // Colored top accent bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(accentColor)
            )

            Column(modifier = Modifier.padding(16.dp)) {
                // Header: icon + name + add button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = style.icon.imageVector,
                        contentDescription = style.icon.label,
                        tint = accentColor,
                        modifier = Modifier.size(24.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = listName,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f),
                    )
                    // Add button
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                BorderStroke(1.5.dp, accentColor),
                                RoundedCornerShape(8.dp),
                            )
                            .clickable { onAddItemClick() }
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add item",
                            tint = accentColor,
                            modifier = Modifier.size(18.dp),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Item list (limited preview)
                val previewItems = items.take(MAX_PREVIEW_ITEMS)
                previewItems.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = item.isDone,
                            onCheckedChange = { onCheckedClick(item.id, item.isDone) },
                            colors = CheckboxDefaults.colors(
                                uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            ),
                        )
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // "View all tasks >" link
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onViewAllClick() },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "View all tasks",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp),
                    )
                }
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun GroupCardViewPreview() {
    ListItTheme {
        GroupCardView(
            listName = "Work",
            items = listOf(
                ListItem(id = "1", title = "Review PR for dashboard", isDone = false),
                ListItem(id = "2", title = "Email Client about specs", isDone = false),
                ListItem(id = "3", title = "Sync Meeting @ 2PM", isDone = false),
                ListItem(id = "4", title = "Hidden fourth item", isDone = false),
            ),
            style = ListCardStyle(
                icon = ListCardIcon.WORK,
                accentColor = ListCardColor.BLUE,
            ),
            onAddItemClick = {},
            onViewAllClick = {},
            onCheckedClick = { _, _ -> },
        )
    }
}