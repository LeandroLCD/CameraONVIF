package com.leandrolcd.cameraonvif.ui.utilities

sealed class DeviceType{
    object Tv: DeviceType()
    object Mobile: DeviceType()
    object Tablet: DeviceType()
    object Unknown: DeviceType()
}

