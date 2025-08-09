package com.example.kursushub.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kursushub.data.local.UserPreferencesRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserPreferencesRepository) : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> = _userEmail

    fun loadUserProfile() {
        val user = auth.currentUser
        _userName.value = user?.displayName ?: "Pengguna"
        _userEmail.value = user?.email ?: "Tidak ada email"
    }

    fun logout() {
        viewModelScope.launch {
            auth.signOut()
            repository.clearUserSession()
        }
    }
}