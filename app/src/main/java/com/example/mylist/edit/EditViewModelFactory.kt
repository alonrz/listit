package com.example.mylist.edit

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.mylist.data.*

@Suppress("UNCHECKED_CAST")
class EditViewModelFactory(
    val itemId: String,
    val itemTitle: String,
    val isDone: Boolean,
    private val application: Application,
    val lifecycle: Lifecycle,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val savedStateHandle = extras.createSavedStateHandle()

        return EditViewModel(
            itemId = itemId,
            itemTitle = mutableStateOf(itemTitle),
            isDone = mutableStateOf(isDone),
            repo = RepoProvider.getRepo(RepoProvider.TypesOfData.Local, application),
            lifecycle = lifecycle,
        ) as T
    }
}