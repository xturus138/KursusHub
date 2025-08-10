package com.example.kursushub.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kursushub.data.model.School
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AppliedSchoolRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend fun addAppliedSchool(school: School) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            firestore.collection("users").document(userId)
                .collection("applied_schools").document(school.namaSekolah.toString()).set(school).await()
        }
    }

    suspend fun isApplied(school: School): Boolean {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            return try {
                val document = firestore.collection("users").document(userId)
                    .collection("applied_schools").document(school.namaSekolah.toString()).get().await()
                document.exists()
            } catch (e: Exception) {
                false
            }
        }
        return false
    }

    fun getAppliedSchools(): LiveData<MutableList<School>> {
        val appliedSchoolsLiveData = MutableLiveData<MutableList<School>>()
        val userId = auth.currentUser?.uid
        if (userId != null) {
            firestore.collection("users").document(userId)
                .collection("applied_schools")
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        return@addSnapshotListener
                    }

                    val appliedSchools = mutableListOf<School>()
                    if (snapshots != null) {
                        for (document in snapshots) {
                            val school = document.toObject(School::class.java)
                            appliedSchools.add(school)
                        }
                    }
                    appliedSchoolsLiveData.value = appliedSchools
                }
        }
        return appliedSchoolsLiveData
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