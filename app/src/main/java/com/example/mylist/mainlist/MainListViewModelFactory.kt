package com.example.mylist.mainlist

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.mylist.data.AppDatabase
import com.example.mylist.data.GenericRepo
import com.example.mylist.data.MainListRepoFakeData
import com.example.mylist.data.MainListRepoLocalData

@Suppress("UNCHECKED_CAST")
class MainListViewModelFactory(
    private val application: Application,
    val lifecycle: Lifecycle,
) : ViewModelProvider.Factory {

    enum class TypesOfData {
        Fake, Local
    }

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val savedStateHandle = extras.createSavedStateHandle()

        return MainListViewModel(
            application = application,
            repo = getRepo(TypesOfData.Local),
            savedStateHandle = savedStateHandle,
            lifecycle = lifecycle,
        ) as T
    }

    private fun getRepo(typeOfRepo: TypesOfData): GenericRepo {
        when (typeOfRepo) {
            TypesOfData.Fake -> return MainListRepoFakeData(lifecycleOwner = null)
            TypesOfData.Local -> {
                val db = AppDatabase.getInstance(context = application)
                val mainListDao = db.mainListDao()
                return MainListRepoLocalData(mainListDAO = mainListDao)
            }
        }
    }
}