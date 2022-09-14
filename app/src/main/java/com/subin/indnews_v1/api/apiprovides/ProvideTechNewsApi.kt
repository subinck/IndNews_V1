package com.subin.indnews_v1.api.apiprovides

import com.subin.indnews_v1.api.FetchTechNewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProvideTechNewsApi {
    @Provides
    @Singleton
    fun provideTechNewsService(retrofit: Retrofit): FetchTechNewsApi {
        return  retrofit.create(FetchTechNewsApi::class.java)
    }
}