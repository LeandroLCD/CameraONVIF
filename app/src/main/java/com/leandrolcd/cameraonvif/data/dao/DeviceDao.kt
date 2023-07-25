package com.leandrolcd.cameraonvif.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.leandrolcd.cameraonvif.data.models.DeviceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {
    @Query("SELECT * from DeviceEntity")
    fun getDevices(): Flow<List<DeviceEntity>>

    @Insert
    suspend fun insertDevice(device: DeviceEntity)

    @Update
    suspend fun editDevice(device: DeviceEntity)

    @Delete
    suspend fun deleteDevice(device: DeviceEntity)

    @Query("SELECT * FROM DeviceEntity WHERE id = :deviceId")
    fun getDeviceById(deviceId: Int): Flow<DeviceEntity?>
}