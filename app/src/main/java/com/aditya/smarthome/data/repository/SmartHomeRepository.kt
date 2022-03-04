package com.aditya.smarthome.data.repository

import com.aditya.smarthome.data.models.Device
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface SmartHomeRepository {

    suspend fun login(email: String, password: String): FirebaseUser?

    suspend fun create(name: String, email: String, password: String): FirebaseUser?

    fun getCurrentUser(): FirebaseUser?

    fun logout()

    fun updateName(id: Int, name: String)

    fun updateStatus(id: Int, status: Int)

    fun getStatus(): Flow<MutableList<Device>>
}