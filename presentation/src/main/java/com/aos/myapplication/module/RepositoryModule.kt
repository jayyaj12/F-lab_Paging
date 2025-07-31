package com.aos.myapplication.module

import com.aos.data.datasource.VideoRemoteDataSourceImpl
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
    fun provideVideoRepository(videoRemoteDataSourceImpl: VideoRemoteDataSourceImpl): VideoRepository =
        VideoRepositoryImpl(videoRemoteDataSourceImpl)

}