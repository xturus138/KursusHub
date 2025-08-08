package com.example.kursushub.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _loginStatus = MutableLiveData<AuthResult>()
    val loginStatus: LiveData<AuthResult> = _loginStatus

    sealed class AuthResult {
        object Loading : AuthResult()
        data class Success(val message: String) : AuthResult()
        data class Error(val error: String) : AuthResult()
    }

    fun loginWithEmail(email: String, password: String) {
        _loginStatus.value = AuthResult.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _loginStatus.value = AuthResult.Success("Login successful")
            }
            .addOnFailureListener { exception ->
                _loginStatus.value = AuthResult.Error(exception.message ?: "Login failed")
            }
    }

    fun loginWithGoogle(idToken: String) {
        _loginStatus.value = AuthResult.Loading
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                _loginStatus.value = AuthResult.Success("Google login successful")
            }
            .addOnFailureListener { exception ->
                _loginStatus.value = AuthResult.Error(exception.message ?: "Google login failed")
            }
    }
}