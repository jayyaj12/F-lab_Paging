package com.aos.myapplication.module

import com.aos.data.api.VideoService
import com.aos.data.datasource.VideoRemoteDataSource
import com.aos.data.datasource.VideoRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideVideoRemoteDataSourceImpl(videoService: VideoService) = VideoRemoteDataSourceImpl(videoService)

}