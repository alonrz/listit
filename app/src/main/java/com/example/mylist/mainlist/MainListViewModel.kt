package com.example.mylist

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.mylist.models.ListItemData
import com.example.mylist.repo.MainListRepo
import kotlin.random.Random

class MainListViewModel constructor(private val mainListRepo: MainListRepo) : ViewModel() {
    private val _list = mutableStateListOf<ListItemData>()
    val items: List<ListItemData> = _list

    fun addItem() {
        _list.add(ListItemData(name = fakeMakeNewEntry(), isChecked = false))
    }

    fun removeItem(id: Int) {
        val item = _list.find { it.id == id }
        item?.let { _list.remove(it) }
    }

    fun changeItemCheckStatus(id: Int, checked: Boolean) {
        val item = _list.find { it.id == id }
        val index = _list.indexOf(item)
        item?.let {
            _list.set(
                index = index, element =
                ListItemData(
                    name = it.name,
                    id = it.id,
                    isChecked = checked,
                )
            )
        }
    }

    private fun fakeMakeNewEntry(): String {
        val rnd = Random
        return when (rnd.nextInt(10)) {
            0 -> "Wash dishes"
            1 -> "Do laundry"
            2 -> "Change lights"
            3 -> "Make coffee"
            4 -> "Go shopping"
            5 -> "Take a walk"
            6 -> "Make your bed"
            7 -> "Draw a picture"
            8 -> "Meditate"
            9 -> "Go for a run"
            else -> "Take a break"
        }
    }
}