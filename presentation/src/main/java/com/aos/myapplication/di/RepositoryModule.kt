package com.aos.myapplication.di

import com.aos.data.api.VideoApi
import com.aos.data.local.dao.VideoDao
import com.aos.data.repository.VideoRepositoryImpl
import com.aos.domain.repository.VideoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideVideoRepository(videoApi: VideoApi, dao: VideoDao): VideoRepository =
        VideoRepositoryImpl(videoApi, dao)

}