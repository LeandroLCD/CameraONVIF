package com.leandrolcd.cameraonvif.data.repository

import com.leandrolcd.cameraonvif.data.dao.DeviceDao
import com.leandrolcd.cameraonvif.data.models.DeviceEntity
import com.leandrolcd.cameraonvif.ui.models.DeviceOnvif
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnvifDeviceRepository @Inject constructor(private val deviceDao: DeviceDao) {

    val getDevices: Flow<List<DeviceOnvif>> =
        deviceDao.getDevices().map { items -> items.map { DeviceOnvif(it.id, it.ip, it.user, it.port, it.password, it.serialNumber) } }

    fun getDeviceById(id: Int): Flow<DeviceOnvif?> {
        return deviceDao.getDeviceById(id).map { item -> item?.let { DeviceOnvif(it.id, it.ip, it.user, it.port, it.password, it.serialNumber) } }
    }

    suspend fun addDevice(device: DeviceOnvif) {
        deviceDao.insertDevice(DeviceEntity(device.id, device.ip, device.user, device.port, device.password, device.serialNumber))
    }

    suspend fun editDevice(device: DeviceOnvif) {
        deviceDao.editDevice(DeviceEntity(device.id, device.ip, device.user, device.port, device.password, device.serialNumber))
    }

    suspend fun deleteDevice(device: DeviceOnvif) {
        deviceDao.deleteDevice(DeviceEntity(device.id, device.ip, device.user, device.port, device.password, device.serialNumber))
    }
}


