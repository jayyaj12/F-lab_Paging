package com.aos.myapplication.module

import com.aos.data.api.VideoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideVideoService(retrofit: Retrofit) = retrofit.create(VideoService::class.java)

}