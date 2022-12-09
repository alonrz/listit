package com.example.mylist.models

import android.view.View

data class ListItemData(
    val name: String,
    val isChecked: Boolean = false,
    val id: Int = View.generateViewId(),
) {

}