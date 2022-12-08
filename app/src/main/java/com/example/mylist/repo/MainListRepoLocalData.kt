package com.example.mylist.repo

import com.example.mylist.models.ListItemData

class MainListRepoLocalData : MainListDAO {
    override fun getMainList(): List<ListItemData> {
        return listOf(
            ListItemData("See a movie"),
            ListItemData("Send letter to Santa"),
            ListItemData("Schedule dentist"),
            ListItemData("Watch TV"),
            ListItemData("Go to the gym"),
        )
    }
}