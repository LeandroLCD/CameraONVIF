package com.leandrolcd.cameraonvif.data.di.network

import be.teletask.onvif.listeners.OnvifResponseListener
import be.teletask.onvif.models.OnvifDevice
import be.teletask.onvif.responses.OnvifResponse

class OnvifResponseListenerImpl: OnvifResponseListener {
    override fun onResponse(onvifDevice: OnvifDevice?, response: OnvifResponse<*>?) {
        TODO("Not yet implemented")
    }

    override fun onError(onvifDevice: OnvifDevice?, errorCode: Int, errorMessage: String?) {
        TODO("Not yet implemented")
    }
}