package com.subin.indnews_v1.di


import com.subin.indnews_v1.BuildConfig
import com.subin.indnews_v1.Constants.ApiPathConstants
import com.subin.indnews_v1.SharedPreference.AppPreference
import com.subin.indnews_v1.SharedPreference.IndNewsSharedPref
import com.subin.indnews_v1.repository.ArticleRepository
import com.subin.indnews_v1.repository.GetRepository.GetArticleRepository
import com.subin.indnews_v1.repository.GetRepository.GetLiveNewsRepository
import com.subin.indnews_v1.repository.GetRepository.GetTechNewsRepository
import com.subin.indnews_v1.repository.LiveNewsRepository
import com.subin.indnews_v1.repository.TechNewsRepository
import com.subin.indnews_v1.usecase.ArticleUseCase
import com.subin.indnews_v1.usecase.GetUseCase.GetArticleUseCase
import com.subin.indnews_v1.usecase.GetUseCase.GetLiveNewsUseCase
import com.subin.indnews_v1.usecase.GetUseCase.GetTechNewsUseCase
import com.subin.indnews_v1.usecase.LiveNewsUseCase
import com.subin.indnews_v1.usecase.TechNewsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private var baseUrl:String=ApiPathConstants.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val builder=  OkHttpClient.Builder()
        builder.connectTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()


    @Module
    @InstallIn(SingletonComponent::class)
    interface AppModuleInt {

        @Binds
        @Singleton
        fun providesArticleRepository(repository:ArticleRepository):GetArticleRepository

        @Binds
        @Singleton
        fun providesArticleUseCase(useCase: ArticleUseCase):GetArticleUseCase

        @Binds
        @Singleton
        fun providesTechNewsRepository(repository:TechNewsRepository):GetTechNewsRepository

        @Binds
        @Singleton
        fun providesTechNewsUseCase(useCase: TechNewsUseCase):GetTechNewsUseCase

        @Binds
        @Singleton
        fun providesLiveNewsRepository(repository:LiveNewsRepository): GetLiveNewsRepository

        @Binds
        @Singleton
        fun providesLiveNewsUseCase(useCase: LiveNewsUseCase): GetLiveNewsUseCase


        @Binds
        abstract fun bindSharedPreferences(appPreferenceImpl: IndNewsSharedPref): AppPreference
    }
}