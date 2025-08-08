package com.example.kursushub.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class RegisterViewModel : ViewModel() {

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
                _registerStatus.value = AuthResult.Success("Registration successful")
            }
            .addOnFailureListener { exception ->
                _registerStatus.value = AuthResult.Error(exception.message ?: "Registration failed")
            }
    }

    fun registerWithGoogle(idToken: String) {
        _registerStatus.value = AuthResult.Loading
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                _registerStatus.value = AuthResult.Success("Google registration successful")
            }
            .addOnFailureListener { exception ->
                _registerStatus.value = AuthResult.Error(exception.message ?: "Google registration failed")
            }
    }
}