package com.aditya.smarthome

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.aditya.smarthome.data.repository.SmartHomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SmartHomeRepository
): ViewModel() {

    private val _userLoggedIn: MutableState<Boolean?> = mutableStateOf(null)
    val userLoggedIn: State<Boolean?> = _userLoggedIn

    init {
        isUserLoggedIn()
    }

    private fun isUserLoggedIn() {
        _userLoggedIn.value = repository.getCurrentUser() != null
    }

}