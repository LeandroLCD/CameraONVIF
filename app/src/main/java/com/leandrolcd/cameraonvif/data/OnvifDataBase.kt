package com.leandrolcd.cameraonvif.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.leandrolcd.cameraonvif.data.dao.DeviceDao
import com.leandrolcd.cameraonvif.data.models.DeviceEntity

@Database(entities = [DeviceEntity::class], version = 1)
abstract class OnvifDataBase : RoomDatabase() {
    abstract fun deviceDao():DeviceDao
}