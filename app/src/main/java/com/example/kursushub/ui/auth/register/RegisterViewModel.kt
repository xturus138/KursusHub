package com.example.kursushub.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kursushub.data.local.UserPreferencesRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserPreferencesRepository) : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _registerStatus = MutableLiveData<AuthResult>()
    val registerStatus: LiveData<AuthResult> = _registerStatus

    sealed class AuthResult {
        object Loading : AuthResult()
        data class Success(val message: String) : AuthResult()
        data class Error(val error: String) : AuthResult()
    }

    fun registerWithEmail(name: String, email: String, password: String) {
        _registerStatus.value = AuthResult.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                result.user?.updateProfile(
                    com.google.firebase.auth.UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()
                )
                val uid = result.user?.uid
                if (uid != null) {
                    viewModelScope.launch {
                        repository.saveUserSession(uid)
                        _registerStatus.value = AuthResult.Success("Registration successful")
                    }
                } else {
                    _registerStatus.value = AuthResult.Error("Failed to get user ID.")
                }
            }
            .addOnFailureListener { exception ->
                _registerStatus.value = AuthResult.Error(exception.message ?: "Registration failed")
            }
    }

    fun registerWithGoogle(idToken: String) {
        _registerStatus.value = AuthResult.Loading
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid
                if (uid != null) {
                    viewModelScope.launch {
                        repository.saveUserSession(uid)
                        _registerStatus.value = AuthResult.Success("Google registration successful")
                    }
                } else {
                    _registerStatus.value = AuthResult.Error("Failed to get user ID.")
                }
            }
            .addOnFailureListener { exception ->
                _registerStatus.value = AuthResult.Error(exception.message ?: "Google registration failed")
            }
    }
}