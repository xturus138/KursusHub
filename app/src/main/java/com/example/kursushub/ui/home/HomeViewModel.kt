package com.example.kursushub.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kursushub.data.model.School
import com.example.kursushub.data.repository.SchoolRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = SchoolRepository()

    private val _schools = MutableLiveData<List<School>>()
    val schools: LiveData<List<School>> = _schools

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getSchools(jenjang: String? = null) {
        viewModelScope.launch {
            try {
                _schools.value = repository.getSchools(jenjang)
            } catch (e: Exception) {
                _error.value = "Failed to fetch data: ${e.message}"
                _schools.value = emptyList()
            }
        }
    }

    fun searchSchools(query: String) {
        viewModelScope.launch {
            try {
                _schools.value = repository.searchSchools(query)
            } catch (e: Exception) {
                _error.value = "Failed to search data: ${e.message}"
                _schools.value = emptyList()
            }
        }
    }
}