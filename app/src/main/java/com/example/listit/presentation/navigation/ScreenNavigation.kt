package com.example.listit.presentation.navigation

const val EDIT_ARGUMENT_KEY = "id"
const val EDIT_ARGUMENT_KEY2 = "title"
const val EDIT_ARGUMENT_KEY3 = "isDone"
const val VIEW_ALL_ARG_GROUP_ID = "groupId"

sealed class ScreenNavigation(val route: String) {

    object Root : ScreenNavigation(route = "root_screen")
    object Edit : ScreenNavigation(
        route = "edit_screen/{$EDIT_ARGUMENT_KEY}/{$EDIT_ARGUMENT_KEY2}/{$EDIT_ARGUMENT_KEY3}") {
        fun passIdAndTitle(id: String, title: String, isDone: Boolean,): String {
            return "edit_screen/$id/$title/$isDone"
        }
    }

    object ViewAll : ScreenNavigation(route = "view_all/{$VIEW_ALL_ARG_GROUP_ID}") {
        fun createRoute(groupId: String): String = "view_all/$groupId"
    }

    object Settings : ScreenNavigation(route = "settings_screen")
}
