package com.example.mylist.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen() {
    Column {
        Text(text = "Settings page")

        Text(
            text = "This is where you enter your text",
            fontSize = 38.sp,
            lineHeight = 30.sp,
        )
        Box(
            Modifier
                .height(1.dp)
                .fillMaxWidth()
        )
    }
}