package com.aditya.smarthome.data.source

import com.aditya.smarthome.data.models.Device
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import com.aditya.smarthome.R

class DeviceApi (
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseStorage: FirebaseStorage
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
                    //TODO throw some error if task failed
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

    fun updateIcon(id: Int, icon: Int) {
        val email = firebaseAuth.currentUser?.email
        email?.let {
            val index = it.indexOf("@")
            val key = it.substring(startIndex = 0, endIndex = index)
            val userDbRef = firebaseDatabase.getReference(key)
            userDbRef.child(id.toString()).child("icon").setValue(icon)
        }
    }

    fun getStatus() = callbackFlow {
        val user = firebaseAuth.currentUser
        user?.let {
            it.email?.let { email ->
                val index = email.indexOf("@")
                val key = email.substring(0, index)
                val icons = getAllIcons()
                firebaseDatabase.getReference(key)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val devices = mutableListOf<Device>()
                            for (deviceSnapshot in snapshot.children) {
                                deviceSnapshot.getValue(Device::class.java)?.let { it ->
                                    val device = it.copy(icon = icons[it.icon - 1])
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

    fun getStatusById(id: Int) = callbackFlow {
        val user = firebaseAuth.currentUser
        user?.let {
            it.email?.let { email ->
                val index = email.indexOf("@")
                val key = email.substring(0, index)
                firebaseDatabase.getReference(key).child(id.toString())
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val device: Device? = snapshot.getValue(Device::class.java)

                            this@callbackFlow.trySend(device).isSuccess
                        }
                        override fun onCancelled(error: DatabaseError) {}
                    })
                awaitClose()
            }
        }
    }

    fun getAllIcons(): List<Int> {
        return listOf(
            R.drawable.ic_1,
            R.drawable.ic_2,
            R.drawable.ic_3,
            R.drawable.ic_4,
            R.drawable.ic_5,
            R.drawable.ic_6,
            R.drawable.ic_7,
            R.drawable.ic_8,
            R.drawable.ic_9,
            R.drawable.ic_10,
            R.drawable.ic_11
        )
    }
}