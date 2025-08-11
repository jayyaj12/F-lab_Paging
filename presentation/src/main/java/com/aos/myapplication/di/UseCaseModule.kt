package com.aos.myapplication.di

import com.aos.domain.repository.VideoRepository
import com.aos.domain.usecase.AddFavoriteVideoUseCase
import com.aos.domain.usecase.DeleteFavoriteVideoUseCase
import com.aos.domain.usecase.ObserveFavoriteVideoUseCase
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
    fun provideAddFavoriteVideoUseCase(videoRepository: VideoRepository) = AddFavoriteVideoUseCase(videoRepository)
    fun provideDeleteFavoriteVideoUseCase(videoRepository: VideoRepository) = DeleteFavoriteVideoUseCase(videoRepository)
    fun provideObserveFavoriteVideoUseCase(videoRepository: VideoRepository) = ObserveFavoriteVideoUseCase(videoRepository)

}