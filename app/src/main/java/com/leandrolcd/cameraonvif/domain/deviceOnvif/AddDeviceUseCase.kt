package com.leandrolcd.cameraonvif.domain.deviceOnvif

import com.leandrolcd.cameraonvif.data.repository.OnvifDeviceRepository
import com.leandrolcd.cameraonvif.ui.models.DeviceOnvif
import javax.inject.Inject

class AddDeviceUseCase @Inject constructor(private val onvifDeviceRepository: OnvifDeviceRepository) {
    suspend fun execute(device: DeviceOnvif) {
        onvifDeviceRepository.addDevice(device)
    }
}
