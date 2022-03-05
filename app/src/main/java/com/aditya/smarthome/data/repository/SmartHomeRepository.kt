package com.aditya.smarthome.data.repository

import com.aditya.smarthome.data.models.Device
import com.aditya.smarthome.util.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface SmartHomeRepository {

    suspend fun login(email: String, password: String): Resource<FirebaseUser>

    suspend fun create(name: String, email: String, password: String): Resource<FirebaseUser>

    fun getCurrentUser(): FirebaseUser?

    fun logout()

    fun updateName(id: Int, name: String)

    fun updateStatus(id: Int, status: Int)

    fun updateIcon(id: Int, icon: Int)

    fun getStatus(): Flow<MutableList<Device>>

    fun getStatusById(id: Int): Flow<Device?>

    fun getAllIcons(): List<Int>
}