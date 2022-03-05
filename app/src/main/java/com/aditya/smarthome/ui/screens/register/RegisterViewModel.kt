package com.aditya.smarthome.ui.screens.register

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.smarthome.data.repository.SmartHomeRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: SmartHomeRepository
) : ViewModel() {

    private val _user: MutableState<FirebaseUser?> = mutableStateOf(null)
    val user: State<FirebaseUser?> = _user

    private val _nameText = mutableStateOf("")
    val nameText: State<String> = _nameText

    private val _emailText = mutableStateOf("")
    val emailText: State<String> = _emailText

    private val _passwordText = mutableStateOf("")
    val passwordText: State<String> = _passwordText

    private val _nameTextError = mutableStateOf(false)
    val nameTextError: State<Boolean> = _nameTextError

    private val _emailTextError = mutableStateOf(false)
    val emailTextError: State<Boolean> = _emailTextError

    private val _passwordTextError = mutableStateOf(false)
    val passwordTextError: State<Boolean> = _passwordTextError

    private val _showProgressBar = mutableStateOf(false)
    val showProgressBar: State<Boolean> = _showProgressBar

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    fun setNameText(value: String) {
        _nameText.value = value
        _nameTextError.value = false
    }

    fun setEmailText(value: String) {
        _emailText.value = value
        _emailTextError.value = false
    }

    fun setPasswordText(value: String) {
        _passwordText.value = value
        _passwordTextError.value = false
    }

    fun registerUser() {
        when {
            emailText.value.isBlank() -> {
                _emailTextError.value = true
                return
            }
            nameText.value.isBlank() -> {
                _nameTextError.value = true
                return
            }
            passwordText.value.isBlank() -> {
                _passwordTextError.value = true
                return
            }
        }
        _showProgressBar.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.create(
                    name = nameText.value,
                    email = emailText.value,
                    password = passwordText.value
                )
            if (user != null) {
                _user.value = user
            } else {
                _errorMessage.value = "Something went wrong"
            }
        }.invokeOnCompletion {
            _showProgressBar.value = false
        }
    }

    fun resetErrorMessage() {
        _errorMessage.value = ""
    }
}