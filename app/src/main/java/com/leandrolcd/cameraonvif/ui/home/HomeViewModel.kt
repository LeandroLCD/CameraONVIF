package com.leandrolcd.cameraonvif.ui.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.teletask.onvif.DiscoveryManager
import be.teletask.onvif.OnvifManager
import be.teletask.onvif.listeners.DiscoveryListener
import be.teletask.onvif.models.Device
import be.teletask.onvif.models.OnvifDevice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val onvifDiscoveryManager: DiscoveryManager,
) : ViewModel() {
    val address = mutableStateOf("")
    val login = mutableStateOf("")
    val password = mutableStateOf("")
    val snapshotUri = mutableStateOf<String?>(null)

    private var device: OnvifDevice? = null

    private val _errorText = MutableStateFlow<String?>(null)
    val errorText = _errorText.asStateFlow()

    private val _image = MutableStateFlow<ByteArray?>(null)
    val image = _image.asStateFlow()

    val discoveredDevices: Flow<List<CameraInformation>> = flow {
        val devicesFound = mutableListOf<CameraInformation>()

        val discoveryListener = object : DiscoveryListener {
            override fun onDiscoveryStarted() {
                // El descubrimiento ha comenzado
                Log.i("HomeViewModel", "Discovery started")
            }

            override fun onDevicesFound(devices: List<Device>) {
                // Se encontraron dispositivos ONVIF
                for (device in devices) {
                    // Agregar información del dispositivo a la lista
                    val cameraInfo = CameraInformation(device.hostName, device.type.ordinal.toString(),device.hostName)
                    devicesFound.add(cameraInfo)
                }
            }
        }

        // Configurar el tiempo de espera para el descubrimiento (en milisegundos)
        onvifDiscoveryManager.discoveryTimeout = 1000
        // Iniciar el descubrimiento con el listener personalizado
        onvifDiscoveryManager.discover(discoveryListener)

        // Esperar un tiempo para que el descubrimiento se complete (ajustar según sea necesario)
        delay(1500)

        // Emitir la lista de dispositivos descubiertos
        emit(devicesFound)
    }.flowOn(Dispatchers.IO)


    fun connectClicked() {
        val address = address.value.trim()
        val login = login.value.trim()
        val password = password.value.trim()

        // Verificar que se haya ingresado la dirección IP, el nombre de usuario y la contraseña
        if (address.isEmpty() || login.isEmpty() || password.isEmpty()) {
            _errorText.value = "Please enter the required fields."
            return
        }


    }

    // ... (código existente para obtener instantáneas)

    fun clearErrorText() {
        _errorText.value = null
    }

    fun clearSnapshot() {
        _image.value = null
    }
}


data class CameraInformation(val friendlyName: String?, val id: String, val host: String)

data class OnvifCachedDevice(val hosts: List<String>, val endpoint: String)