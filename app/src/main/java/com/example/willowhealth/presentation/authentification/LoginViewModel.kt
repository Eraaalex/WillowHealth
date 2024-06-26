package com.example.willowhealth.presentation.authentification

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.willowhealth.model.LoginUiState
import com.example.willowhealth.service.AccountServiceImpl
import com.example.willowhealth.utils.isValidEmail
import com.example.willowhealth.utils.isValidPassword
import com.example.willowhealth.utils.isValidPhone
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val accountService: AccountServiceImpl) : ViewModel() {
    var navigateToMainScreen = MutableStateFlow(false)
        private set

    var snackbarMessage = MutableStateFlow("")
        private set

    var uiState = mutableStateOf(LoginUiState())
        private set
    private val email
        get() = uiState.value.email
    private val phone
        get() = uiState.value.phone
    private val password
        get() = uiState.value.password

    private val user = MutableLiveData<FirebaseUser>()

    var allValidationPassed = mutableStateOf(false)

    init {
        FirebaseAuth.getInstance().addAuthStateListener {
            if (it.currentUser != null) {
                user.value = it.currentUser
            }
        }
    }

    fun onSignInClick() {
        viewModelScope.launch {
            val response = accountService.authenticate(email, password)
            if (response) {
                navigateToMainScreen.value = true

            } else {
                snackbarMessage.value = "Sign-in failed. Please try again."
            }
        }
    }

    fun clearSnackbarMessage() {
        snackbarMessage.value = ""
    }

    fun onSignUpClick() {
        viewModelScope.launch {
            val result =
                accountService.register(email, password)
            if (result) {
                navigateToMainScreen.value = true
            } else {
                snackbarMessage.value = "Sign Up error, check your input data!"
            }
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

    fun resetNavigationTrigger() {
        navigateToMainScreen.value = false
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            val result = accountService.resetPassword(email)
            if (result) {
                snackbarMessage.value = "Password reset email sent!"
            } else {
                snackbarMessage.value = "Password reset failed!"
            }
        }
    }
}

