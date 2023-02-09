package com.example.mylist.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun ListItemView(
    title: String,
    id: String,
    onCheckedClick: (Boolean) -> Unit,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
    isDone: Boolean = false,
) {
    Card(
        modifier = Modifier.padding(8.dp),
        shape = CardDefaults.outlinedShape,
        border = BorderStroke(width = 1.dp, color = if (isDone) Color.Gray else Color.Black)
    ) {
        Row(
            modifier = modifier
                .padding(all = 0.dp)
                .fillMaxWidth()
                .clickable { onItemClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isDone,
                onCheckedChange = onCheckedClick,
            )
            Text(
                text = title,
                style = TextStyle(
                    textDecoration = if (isDone) TextDecoration.LineThrough else TextDecoration.None
                ),
            )
        }
    }
}