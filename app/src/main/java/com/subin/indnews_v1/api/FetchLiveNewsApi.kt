package com.subin.indnews_v1.api

import com.subin.indnews_v1.Constants.ApiPathConstants
import com.subin.indnews_v1.model.response.LiveNewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FetchLiveNewsApi {
    @GET(ApiPathConstants.getLiveNews)
    suspend fun getLiveNews(@Query("country")articleType:String="in",
                            @Query("apiKey")apiKey:String= ApiPathConstants.API_KEY): Response<LiveNewsResponse>
}