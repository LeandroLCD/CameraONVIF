package com.leandrolcd.cameraonvif.ui.utilities

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.seanproctor.onvifcamera.OnvifDevice
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HeaderValueParam
import io.ktor.http.contentType
import io.ktor.utils.io.errors.IOException
import java.io.ByteArrayOutputStream

const val getSystemDateAndTimeCommand = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://www.w3.org/2003/05/soap-envelope\"" +
        " xmlns:tds=\"http://www.onvif.org/ver10/device/wsdl\">" +
        " <SOAP-ENV:Body>" +
        "  <tds:GetSystemDateAndTime/>" +
        " </SOAP-ENV:Body>" +
        "</SOAP-ENV:Envelope> "

private val soapContentType: ContentType =
    ContentType(
        contentType = "application",
        contentSubtype = "soap+xml",
        parameters = listOf(HeaderValueParam("charset", "utf-8"))
    )

fun Context.getDeviceType(): DeviceType {
    val configuration = this.resources.configuration

    return when (configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) {
        Configuration.SCREENLAYOUT_SIZE_XLARGE -> DeviceType.Tablet
        Configuration.SCREENLAYOUT_SIZE_LARGE -> DeviceType.Tablet
        Configuration.SCREENLAYOUT_SIZE_NORMAL -> DeviceType.Mobile
        Configuration.SCREENLAYOUT_SIZE_SMALL -> DeviceType.Mobile
        else -> {
            when (configuration.uiMode and Configuration.UI_MODE_TYPE_MASK) {
                Configuration.UI_MODE_TYPE_TELEVISION -> DeviceType.Tv
                else -> DeviceType.Unknown
            }
        }
    }
}

suspend fun OnvifDevice.Companion.isReachableEndpoint(url: String): Boolean {
    try {
        HttpClient().use { client ->
            val response = client.post(url) {
                contentType(soapContentType)
                setBody(getSystemDateAndTimeCommand)
            }
            return response.status.value in 200..299
        }
    } catch (e: IOException) {
        return false
    }
}

fun ByteArray.toBitmap(): Bitmap {
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}
fun Bitmap.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}