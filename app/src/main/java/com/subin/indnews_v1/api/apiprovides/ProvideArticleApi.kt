package com.subin.indnews_v1.api.apiprovides

import com.subin.indnews_v1.api.FetchArticleApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProvideArticleApi {
    @Provides
    @Singleton
    fun provideArticleService(retrofit: Retrofit): FetchArticleApi {
        return  retrofit.create(FetchArticleApi::class.java)
    }
}