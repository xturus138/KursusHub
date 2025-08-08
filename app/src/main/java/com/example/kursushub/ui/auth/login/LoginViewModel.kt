package com.example.kursushub.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kursushub.data.local.UserPreferencesRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserPreferencesRepository) : ViewModel() {

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
            .addOnSuccessListener { result ->
                val uid = result.user?.uid
                if (uid != null) {
                    viewModelScope.launch {
                        repository.saveUserSession(uid)
                        _loginStatus.value = AuthResult.Success("Login successful")
                    }
                } else {
                    _loginStatus.value = AuthResult.Error("Failed to get user ID.")
                }
            }
            .addOnFailureListener { exception ->
                _loginStatus.value = AuthResult.Error(exception.message ?: "Login failed")
            }
    }

    fun loginWithGoogle(idToken: String) {
        _loginStatus.value = AuthResult.Loading
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid
                if (uid != null) {
                    viewModelScope.launch {
                        repository.saveUserSession(uid)
                        _loginStatus.value = AuthResult.Success("Google login successful")
                    }
                } else {
                    _loginStatus.value = AuthResult.Error("Failed to get user ID.")
                }
            }
            .addOnFailureListener { exception ->
                _loginStatus.value = AuthResult.Error(exception.message ?: "Google login failed")
            }
    }
}