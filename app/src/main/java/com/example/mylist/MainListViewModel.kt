package com.example.mylist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mylist.models.ListItemData
import com.example.mylist.repo.MainListRepo
import kotlin.random.Random

data class Items(
    val list: MutableList<ListItemData> = mutableListOf()
)
class MainListViewModel constructor(private val mainListRepo: MainListRepo): ViewModel() {
    val items = mutableStateListOf<ListItemData>()

    fun addItem() {
        items.add(ListItemData(name = fakeMakeNewEntry(), status = false))
    }

    fun removeItem(id: Int) {

    }

    private fun fakeMakeNewEntry(): String {
        val rnd = Random
        return when(rnd.nextInt(10)){
            0-> "Wash dishes"
            1-> "Do laundry"
            2-> "Change lights"
            3-> "Make coffee"
            4-> "Go shopping"
            5-> "Take a walk"
            6-> "Make your bed"
            7-> "Draw a picture"
            8-> "Meditate"
            9-> "Go for a run"
            else -> "Take a break"
        }
    }
}