package com.example.mylist.navigation

const val EDIT_ARGUMENT_KEY = "id"
const val EDIT_ARGUMENT_KEY2 = "title"
const val EDIT_ARGUMENT_KEY3 = "isDone"

sealed class ScreenNavigation(val route: String) {

    object Root : ScreenNavigation(route = "root_screen")
    object Edit : ScreenNavigation(
        route = "edit_screen/{$EDIT_ARGUMENT_KEY}/{$EDIT_ARGUMENT_KEY2}/{$EDIT_ARGUMENT_KEY3}") {
        fun passIdAndTitle(id: String, title: String, isDone: Boolean,): String {
            return "edit_screen/$id/$title/$isDone"
        }
    }

    object Settings : ScreenNavigation(route = "settings_screen")
}
