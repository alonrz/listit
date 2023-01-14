package com.example.mylist.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ListItemView(
    title: String,
    id: String,
    onCheckedClick: (Boolean) -> Unit,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
) {
    Card(
        modifier = Modifier.padding(8.dp),
        shape =   CardDefaults.outlinedShape,
        border = BorderStroke(width = 1.dp, color = Color.Black)
    ) {
        Row(
            modifier = modifier
                .padding(all = 0.dp)
                .fillMaxWidth()
                .clickable { onItemClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckedClick,
            )
            Text(
                text = title,
            )
        }
    }
}