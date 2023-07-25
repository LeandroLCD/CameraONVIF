package com.leandrolcd.cameraonvif.ui.models

import com.leandrolcd.cameraonvif.data.models.DeviceEntity

data class DeviceOnvif(
    val id:Int,
    val ip:String,
    val user:String = "Admin",
    val port:Int = 8899,
    val password:String = "",
    val serialNumber:String
)


