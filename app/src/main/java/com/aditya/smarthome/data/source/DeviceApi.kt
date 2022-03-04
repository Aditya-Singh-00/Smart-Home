package com.aditya.smarthome.data.source

import com.aditya.smarthome.data.models.Device
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class DeviceApi @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) {
    companion object {
        val default = listOf(
            Device(
                id = 0,
                name = "Device 1",
                status = 0
            ),
            Device(
                id = 1,
                name = "Device 2",
                status = 0
            ),
            Device(
                id = 2,
                name = "Device 3",
                status = 0
            ),
            Device(
                id = 3,
                name = "Device 4",
                status = 0
            )
        )
    }

    fun updateName(id: Int, name: String) {
        val email = firebaseAuth.currentUser?.email
        email?.let {
            val index = it.indexOf("@")
            val key = it.substring(0,index)
            val userDbRef = firebaseDatabase.getReference(key)
            userDbRef.child(id.toString()).child("name").setValue(name).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //TODO
                } else {
                    //TODO throw some error for the popup context
                }
            }
        }
    }

    fun updateStatus(id: Int, value: Int) {
        val email = firebaseAuth.currentUser?.email
        email?.let {
            val index = it.indexOf("@")
            val key = it.substring(startIndex = 0, endIndex = index)
            val userDbRef = firebaseDatabase.getReference(key)
            userDbRef.child(id.toString()).child("status").setValue(value)
        }
    }

    fun getStatus() = callbackFlow {
        val user = firebaseAuth.currentUser
        user?.let {
            it.email?.let { email ->
                val index = email.indexOf("@")
                val key = email.substring(0, index)
                firebaseDatabase.getReference(key)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val devices = mutableListOf<Device>()
                            for (deviceSnapshot in snapshot.children) {
                                deviceSnapshot.getValue(Device::class.java)?.let { device ->
                                    devices.add(device)
                                }
                            }
                            this@callbackFlow.trySend(devices).isSuccess
                        }
                        override fun onCancelled(error: DatabaseError) {}
                    })
                awaitClose()
            }
        }
    }
}