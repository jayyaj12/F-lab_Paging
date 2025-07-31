package com.aos.myapplication.module

import com.aos.domain.repository.VideoRepository
import com.aos.domain.usecase.SearchVideoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideSelectVideoUseCase(videoRepository: VideoRepository) = SearchVideoUseCase(videoRepository)

}