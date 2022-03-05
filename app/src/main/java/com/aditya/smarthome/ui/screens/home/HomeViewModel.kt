package com.aditya.smarthome.ui.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.smarthome.data.models.Device
import com.aditya.smarthome.data.repository.SmartHomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: SmartHomeRepository
) : ViewModel() {

    private val _username = mutableStateOf("")
    val username: State<String> = _username

    private val _devices: MutableState<List<Device>> = mutableStateOf(listOf())
    val devices: State<List<Device>> = _devices

    private val _logoutIconClicked: MutableState<Boolean> = mutableStateOf(false)
    val logoutIconClicked: State<Boolean> = _logoutIconClicked

    init {
        getCurrentUser()
        getDeviceStatus()
    }

    fun updateStatus(deviceId: Int, status: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateStatus(deviceId, status)
        }
    }

    fun logout() {
        repository.logout()
    }

    fun dismissLogoutDialog() {
        _logoutIconClicked.value = false
    }

    private fun getCurrentUser() {
        _username.value = repository.getCurrentUser()?.displayName ?: ""
    }

    private fun getDeviceStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getStatus().collect {
                _devices.value = it
            }
        }
    }
}

