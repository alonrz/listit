package com.example.listit.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.listit.ui.theme.ListItTheme

/**
 * A selectable chip representing a group/list, showing the group's icon and name
 * with a filled accent-color background when selected.
 */
@Composable
fun GroupChip(
    name: String,
    icon: ListCardIcon,
    accentColor: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val backgroundColor = if (isSelected) {
        accentColor
    } else {
        MaterialTheme.colorScheme.surfaceContainerHighest
    }
    val contentColor = if (isSelected) {
        Color.White
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon.imageVector,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(18.dp),
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            ),
            color = contentColor,
        )
    }
}

@Composable
@PreviewLightDark
private fun GroupChipSelectedPreview() {
    ListItTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            GroupChip(
                name = "Work",
                icon = ListCardIcon.WORK,
                accentColor = ListCardColor.BLUE.color,
                isSelected = true,
                onClick = {},
            )
            GroupChip(
                name = "Shopping",
                icon = ListCardIcon.SHOPPING,
                accentColor = ListCardColor.GREEN.color,
                isSelected = false,
                onClick = {},
            )
        }
    }
}
