package com.leandrolcd.cameraonvif.data.di.network

import androidx.annotation.Keep
import be.teletask.onvif.DiscoveryManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DiscoveryManagerModule {
    @Keep
    @Provides
    @Singleton
    fun providerDiscoveryManager() = DiscoveryManager()
}