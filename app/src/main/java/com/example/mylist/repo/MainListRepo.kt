package com.example.mylist.repo

import com.example.mylist.models.ListItemData

/*
Following repository pattern in article https://medium.com/swlh/repository-pattern-in-android-c31d0268118c
 */
class MainListRepo(private val mainListDAO: MainListDAO) {
    /*suspend*/ fun getMainList(): List<ListItemData> = mainListDAO.getMainList()
}

interface MainListDAO { /*Data Access Object*/
    /*suspend*/ fun getMainList(): List<ListItemData>
}