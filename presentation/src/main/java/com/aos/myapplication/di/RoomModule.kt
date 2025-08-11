package com.aos.myapplication.di

import android.content.Context
import androidx.room.Room
import com.aos.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "video.db").build()

    @Singleton
    @Provides
    fun provideVideoDao(appDatabase: AppDatabase) = appDatabase.videoDao()

}

