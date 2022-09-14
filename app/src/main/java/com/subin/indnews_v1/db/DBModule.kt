package com.subin.indnews_v1.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DBModule {
    @Provides
    @Singleton
    fun provideDB(@ApplicationContext app: Context)= Room.databaseBuilder(
        app,
        IndNewsDatabase::class.java,
        "IndNews"
    ).build()

    @Provides
    @Singleton
    fun providesDao(dataBase: IndNewsDatabase):IndNewsDAO{
        return dataBase.providesDAO()
    }
}