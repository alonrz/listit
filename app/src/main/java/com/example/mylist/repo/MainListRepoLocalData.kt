package com.example.mylist.repo

import com.example.mylist.models.ListItemData

class MainListRepoLocalData : MainListDAO {
    override fun getMainList(): List<ListItemData> {
        return listOf(
            ListItemData(name = "See a movie"),
            ListItemData(name = "Send letter to Santa"),
            ListItemData(name = "Schedule dentist"),
            ListItemData(name = "Watch TV"),
            ListItemData(name = "Go to the gym"),
        )
    }
}