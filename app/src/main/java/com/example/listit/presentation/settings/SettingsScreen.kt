package com.example.listit.presentation.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listit.ui.theme.ListItTheme

/**
 * Placeholder settings screen with static text content.
 * Will house app-level configuration options in the future.
 */
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

@Composable
@PreviewLightDark
private fun SettingsScreenPreview() {
    ListItTheme {
        SettingsScreen()
    }
}