package com.example.kursushub.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.kursushub.data.local.UserPreferencesRepository

class MainViewModel(private val repository: UserPreferencesRepository) : ViewModel() {
    fun getUserSession(): LiveData<Pair<String?, Boolean>> {
        return repository.getUserSession().asLiveData()
    }
}