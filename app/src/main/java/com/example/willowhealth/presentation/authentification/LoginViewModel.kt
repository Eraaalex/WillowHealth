package com.example.willowhealth.presentation.authentification

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.willowhealth.data.datasource.FirebaseAuthDataSource
import com.example.willowhealth.extention.isValidEmail
import com.example.willowhealth.extention.isValidPassword
import com.example.willowhealth.extention.isValidPhone
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel : ViewModel() {
    var uiState = mutableStateOf(LoginUiState())
        private set

    private val email
        get() = uiState.value.email
    private val phone
        get() = uiState.value.phone
    private val password
        get() = uiState.value.password

    private val user = MutableLiveData<FirebaseUser>()
    private val error = MutableLiveData<String>()
    private val message = MutableLiveData<String>()
    private val _showSnackbarState = MutableLiveData(false)
    val showSnackbarState: LiveData<Boolean> = _showSnackbarState

    var allValidationPassed = mutableStateOf(false)

    init {
        FirebaseAuth.getInstance().addAuthStateListener {
            if (it.currentUser != null) {
                user.value = it.currentUser
            }
        }
    }

    fun onSignInClick() { // Вход
        _showSnackbarState.value = false
        FirebaseAuthDataSource.signInWithEmailAndPassword(
            uiState.value.email,
            uiState.value.password
        )
            .addOnSuccessListener {
                user.value = it.user

            }.addOnFailureListener {
                error.value = it.message
            }
    }

    fun onSignUpClick() {
        FirebaseAuthDataSource.register(
            uiState.value.email,
            uiState.value.phone,
            uiState.value.password
        )
        FirebaseAuthDataSource.signInWithEmailAndPassword(
            uiState.value.email,
            uiState.value.password
        )
            .addOnSuccessListener {
                user.value = it.user

            }.addOnFailureListener {
                error.value = it.message
            }
    }


    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
        allValidateInputs()
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
        allValidateInputs()
    }

    fun onPhoneChange(newValue: String) {
        uiState.value = uiState.value.copy(phone = newValue)
        allValidateInputs()
    }


    private fun allValidateInputs() {
        allValidationPassed.value = email.isValidEmail() &&
                phone.isValidPhone() &&
                password.isValidPassword()
    }
}

