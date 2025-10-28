package com.example.mylist.mainlist

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.mylist.data.*

@Suppress("UNCHECKED_CAST")
class MainListViewModelFactory(
    private val application: Application,
    val lifecycle: Lifecycle,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val savedStateHandle = extras.createSavedStateHandle()

        return MainListViewModel(
            application = application,
            repo = RepoProvider.getRepo(RepoProvider.TypesOfData.Local, application),
            savedStateHandle = savedStateHandle,
            lifecycle = lifecycle,
        ) as T
    }
}