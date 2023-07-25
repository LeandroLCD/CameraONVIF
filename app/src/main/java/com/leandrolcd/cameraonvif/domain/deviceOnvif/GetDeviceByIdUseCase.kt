package com.leandrolcd.cameraonvif.domain.deviceOnvif

import com.leandrolcd.cameraonvif.data.repository.OnvifDeviceRepository
import com.leandrolcd.cameraonvif.ui.models.DeviceOnvif
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDeviceByIdUseCase @Inject constructor(private val onvifDeviceRepository: OnvifDeviceRepository) {
    suspend fun execute(id: Int): Flow<DeviceOnvif?> {
        return onvifDeviceRepository.getDeviceById(id)
    }
}
