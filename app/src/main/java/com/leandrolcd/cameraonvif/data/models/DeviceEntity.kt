package com.leandrolcd.cameraonvif.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DeviceEntity")
data class DeviceEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val ip:String,
    val user:String,
    val port:Int,
    val password:String,
    val serialNumber:String
        )