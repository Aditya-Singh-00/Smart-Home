package com.aditya.smarthome.data.source

import com.aditya.smarthome.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class AuthorizationApi(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) {

    suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val user = firebaseAuth.signInWithEmailAndPassword(email, password).await().user
            if (user != null) {
                Resource.Success(user)
            } else {
                Resource.Error("Something went wrong")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Something went wrong")
        }
    }

    suspend fun create(name: String, email: String, password: String): Resource<FirebaseUser> {
        return try {
            val user = firebaseAuth.createUserWithEmailAndPassword(email, password).await().user
            if (user != null) {
                val profile = UserProfileChangeRequest.Builder().setDisplayName(name).build()
                user.updateProfile(profile).await()
                user.sendEmailVerification()
                createUserDevicesInDatabase(email)
                Resource.Success(user)
            } else {
                Resource.Error("Something went wrong")
            }
        } catch(e: Exception) {
            Resource.Error(e.localizedMessage ?: "Something went wrong")
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