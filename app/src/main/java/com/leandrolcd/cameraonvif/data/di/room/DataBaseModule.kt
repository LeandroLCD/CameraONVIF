package com.leandrolcd.cameraonvif.data.di.room

import android.content.Context
import androidx.room.Room
import com.leandrolcd.cameraonvif.data.OnvifDataBase
import com.leandrolcd.cameraonvif.data.dao.DeviceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Provides
    fun provideDeviceDao(dataBase: OnvifDataBase):DeviceDao{
       return  dataBase.deviceDao()
    }


    @Provides
    @Singleton
    fun provideDeviceDB(@ApplicationContext appContext: Context): OnvifDataBase{
        return Room.databaseBuilder(appContext, OnvifDataBase::class.java,"OnvifDataBase").build()
    }

}