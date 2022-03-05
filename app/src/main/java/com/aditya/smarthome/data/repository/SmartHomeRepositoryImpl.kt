package com.aditya.smarthome.data.repository

import com.aditya.smarthome.data.models.Device
import com.aditya.smarthome.data.source.AuthorizationApi
import com.aditya.smarthome.data.source.DeviceApi
import com.aditya.smarthome.util.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SmartHomeRepositoryImpl @Inject constructor(
    private val authorization: AuthorizationApi,
    private val device: DeviceApi
): SmartHomeRepository {
    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return authorization.login(email,password)
    }

    override suspend fun create(name: String, email: String, password: String): Resource<FirebaseUser> {
        return authorization.create(name,email,password)
    }

    override fun getCurrentUser(): FirebaseUser? {
        return authorization.getCurrentUser()
    }

    override fun logout() {
        authorization.logout()
    }

    override fun updateName(id: Int, name: String) {
        device.updateName(id,name)
    }

    override fun updateStatus(id: Int, status: Int) {
        device.updateStatus(id,status)
    }

    override fun updateIcon(id: Int, icon: Int) {
        device.updateIcon(id,icon)
    }

    override fun getStatus(): Flow<MutableList<Device>> {
        return device.getStatus()
    }

    override fun getStatusById(id: Int): Flow<Device?> {
        return device.getStatusById(id)
    }

    override fun getAllIcons(): List<Int> {
        return device.getAllIcons()
    }
}