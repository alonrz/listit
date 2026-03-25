package com.example.listit.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class ListCardStyle(
    val icon: ListCardIcon,
    val accentColor: ListCardColor,
)

enum class ListCardIcon(val imageVector: ImageVector, val label: String) {
    WORK(Icons.Filled.Build, "Work"),
    HOME(Icons.Filled.Home, "Home"),
    SHOPPING(Icons.Filled.ShoppingCart, "Shopping"),
    STAR(Icons.Filled.Star, "Star"),
    HEART(Icons.Filled.Favorite, "Heart"),
    PERSON(Icons.Filled.Person, "Personal"),
    CALENDAR(Icons.Filled.DateRange, "Calendar"),
    THUMBS_UP(Icons.Filled.ThumbUp, "Thumbs Up"),
}

enum class ListCardColor(val color: Color, val label: String) {
    BLUE(Color(0xFF5B8DEF), "Blue"),
    RED(Color(0xFFEF5B5B), "Red"),
    GREEN(Color(0xFF5BEF8D), "Green"),
    ORANGE(Color(0xFFEFA85B), "Orange"),
    PURPLE(Color(0xFFAB5BEF), "Purple"),
    TEAL(Color(0xFF5BEFEF), "Teal"),
    PINK(Color(0xFFEF5BBF), "Pink"),
    YELLOW(Color(0xFFEFD95B), "Yellow"),
}

fun randomListCardStyle(usedStyles: List<ListCardStyle> = emptyList()): ListCardStyle {
    val usedIcons = usedStyles.map { it.icon }.toSet()
    val usedColors = usedStyles.map { it.accentColor }.toSet()

    val availableIcons = ListCardIcon.entries.filter { it !in usedIcons }
    val availableColors = ListCardColor.entries.filter { it !in usedColors }

    val icon = availableIcons.randomOrNull() ?: ListCardIcon.entries.random()
    val color = availableColors.randomOrNull() ?: ListCardColor.entries.random()

    return ListCardStyle(icon = icon, accentColor = color)
}