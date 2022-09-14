package com.subin.indnews_v1.api

import com.subin.indnews_v1.Constants.ApiPathConstants
import com.subin.indnews_v1.model.response.BreakingNews
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FetchBreakingNews {
    @GET(ApiPathConstants.getBreakingNews)
    suspend fun getArticles(@Query("apiKey")apiKey:String= ApiPathConstants.API_KEY,
                              @Query("q")articleType:String="live",
                              @Query("country")country:String="in",
                               @Query("page")page:Int):Response<BreakingNews>

}