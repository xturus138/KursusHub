package com.example.kursushub.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kursushub.data.model.School

class AppliedSchoolRepository {
    private val _appliedSchools = MutableLiveData<MutableList<School>>(mutableListOf())
    val appliedSchools: LiveData<MutableList<School>> = _appliedSchools

    fun addAppliedSchool(school: School) {
        val currentList = _appliedSchools.value ?: mutableListOf()
        if (!currentList.contains(school)) {
            currentList.add(school)
            _appliedSchools.value = currentList
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AppliedSchoolRepository? = null

        fun getInstance(): AppliedSchoolRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = AppliedSchoolRepository()
                INSTANCE = instance
                instance
            }
        }
    }
}