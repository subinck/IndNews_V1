package com.subin.indnews_v1.api.apiprovides

import com.subin.indnews_v1.api.FetchLiveNewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProvideLiveNewsApi {
    @Provides
    @Singleton
    fun provideLiveNewsService(retrofit: Retrofit): FetchLiveNewsApi {
        return  retrofit.create(FetchLiveNewsApi::class.java)
    }
}