package com.aditya.smarthome.ui.screens.device_detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.smarthome.data.models.Device
import com.aditya.smarthome.data.repository.SmartHomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceDetailViewModel @Inject constructor(
    private val repository: SmartHomeRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _device: MutableState<Device?> = mutableStateOf(null)
    val device : State<Device?> = _device

    private val _allIcons: MutableState<List<String>> = mutableStateOf(listOf())
    val allIcons: State<List<String>> = _allIcons

    private val _showEditDevice: MutableState<Boolean> = mutableStateOf(false)
    val showEditDevice: State<Boolean> = _showEditDevice

    init {
        getAllIcons()
        getDevice()
    }

    private val _deviceNameText: MutableState<String> = mutableStateOf(device.value?.name ?: "")
    val deviceNameText: State<String> = _deviceNameText

    fun updateDeviceName() {
        device.value?.let {
            viewModelScope.launch(Dispatchers.IO) {
                repository.updateName(it.id,deviceNameText.value)
            }
        }
    }

    fun updateDeviceStatus(status: Int) {
        device.value?.let {
            viewModelScope.launch(Dispatchers.IO) {
                repository.updateStatus(it.id,status)
            }
        }
    }

    fun updateDeviceIcon(icon: String) {
        device.value?.let {
            viewModelScope.launch(Dispatchers.IO) {
                repository.updateIcon(it.id, icon)
            }
        }
    }

    fun toggleEditDevice() {
        _showEditDevice.value = !showEditDevice.value
    }

    fun onDeviceNameTextChange(value: String) {
        _deviceNameText.value = value
    }

    private fun getDevice() {
        val id = savedStateHandle.get<Int>("id")
        id?.let {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getStatusById(id).collect {
                    _device.value = it
                }
            }
        }
    }

    private fun getAllIcons() {
        viewModelScope.launch(Dispatchers.IO) {
            _allIcons.value = repository.getAllIcons()
        }
    }
}