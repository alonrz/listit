package com.example.mylist.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen() {
    Column() {
        Text(text = "Settings page")

        Text(
            text = "This is where you enter your text",
            color = Color(red = 0, green = 0, blue = 0), // color values from 0 to 255
            fontSize = 38.sp,
            lineHeight = 30.sp,
        )
        Box(
            Modifier
                .background(Color.Red)
                .height(1.dp)
                .fillMaxWidth()
        )
    }
}