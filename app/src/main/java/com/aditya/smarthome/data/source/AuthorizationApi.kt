package com.aditya.smarthome.data.source

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthorizationApi @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) {

    suspend fun login(email: String, password: String): FirebaseUser? {
        return firebaseAuth.signInWithEmailAndPassword(email, password).await().user
    }

    suspend fun create(name: String, email: String, password: String): FirebaseUser? {

        val user = firebaseAuth.createUserWithEmailAndPassword(email, password).await().user
        return if (user != null) {
            val profile = UserProfileChangeRequest.Builder().setDisplayName(name).build()
            user.updateProfile(profile).await()
            user.sendEmailVerification()
            createUserDevicesInDatabase(email)
            user
        } else {
            null
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun logout() {
        firebaseAuth.signOut()
    }

    private suspend fun createUserDevicesInDatabase(email: String) {
        val index = email.indexOf("@")
        val key = email.substring(startIndex = 0,endIndex = index)
        val userDbRef = firebaseDatabase.getReference(key)
        DeviceApi.default.forEach { device ->
            userDbRef.child(device.id.toString()).setValue(device).await()
        }
    }

}