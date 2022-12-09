package com.example.mylist.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListItemView(
    title: String,
    id: Int,
    onCheckedClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
) {
    Row(modifier = modifier.padding(all = 0.dp), verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = isChecked,
            onCheckedChange =  onCheckedClick,
        )
        Text(text = title)
    }
}