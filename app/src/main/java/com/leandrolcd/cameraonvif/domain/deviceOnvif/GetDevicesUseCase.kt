package com.leandrolcd.cameraonvif.domain.deviceOnvif

import com.leandrolcd.cameraonvif.data.repository.OnvifDeviceRepository
import com.leandrolcd.cameraonvif.ui.models.DeviceOnvif
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDevicesUseCase @Inject constructor(private val onvifDeviceRepository: OnvifDeviceRepository) {
    fun execute(): Flow<List<DeviceOnvif>> {
        return onvifDeviceRepository.getDevices
    }
}
