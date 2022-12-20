package com.example.mylist.data

import android.app.Application

object RepoProvider {
    enum class TypesOfData {
        Fake, Local
    }

    fun getRepo(
        typeOfRepo: TypesOfData,
        application: Application,
    ): GenericRepo {
        return when (typeOfRepo) {
            TypesOfData.Fake ->
                MainListRepoFakeData(lifecycleOwner = null)
            TypesOfData.Local -> {
                val db = AppDatabase.getInstance(context = application)
                val mainListDao = db.mainListDao()
                MainListRepoLocalData(mainListDAO = mainListDao)
            }
        }
    }
}