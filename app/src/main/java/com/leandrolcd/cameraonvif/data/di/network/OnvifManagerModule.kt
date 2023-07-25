package com.leandrolcd.cameraonvif.data.di.network

import android.content.Context
import androidx.annotation.Keep
import be.teletask.onvif.OnvifManager
import be.teletask.onvif.listeners.OnvifResponseListener
import com.ivanempire.lighthouse.LighthouseClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


//@Module
//@InstallIn(SingletonComponent::class)
//object OnvifManagerModule {
//
//    @Keep
//    @Provides
//    @Singleton
//        fun providerOnvifManager(listener: OnvifResponseListener): OnvifManager = OnvifManager(listener)
//}