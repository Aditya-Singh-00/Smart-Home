package com.aditya.smarthome.data.models

data class Device(
    val id: Int = 0,
    var icon: Int = 1,
    var name: String = "",
    var status: Int = 0
)