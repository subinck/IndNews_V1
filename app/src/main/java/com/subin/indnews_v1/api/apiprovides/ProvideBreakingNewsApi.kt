package com.subin.indnews_v1.api.apiprovides

import com.subin.indnews_v1.api.FetchBreakingNews
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ProvideBreakingNewsApi {
    @Provides
    @Singleton
    fun provideBreakingNewsService(retrofit: Retrofit):FetchBreakingNews{
        return retrofit.create(FetchBreakingNews::class.java)
    }

}