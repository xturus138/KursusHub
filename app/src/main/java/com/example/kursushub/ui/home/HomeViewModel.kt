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

    private var currentPage = 1
    var isLoading = false
    private var isLastPage = false

    fun loadSchools(jenjang: String?, reset: Boolean = false) {
        if (isLoading || (isLastPage && !reset)) return

        if (reset) {
            currentPage = 1
            isLastPage = false
        }

        viewModelScope.launch {
            isLoading = true
            try {
                val newSchools = repository.getSchools(jenjang, currentPage)
                if (newSchools.isEmpty()) {
                    isLastPage = true
                } else {
                    val currentSchools = if (reset) mutableListOf() else _schools.value?.toMutableList() ?: mutableListOf()
                    currentSchools.addAll(newSchools)
                    _schools.value = currentSchools
                    currentPage++
                }
            } catch (e: Exception) {
                _error.value = "Failed to fetch data: ${e.message}"
            } finally {
                isLoading = false
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