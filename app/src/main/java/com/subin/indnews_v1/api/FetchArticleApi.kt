package com.subin.indnews_v1.api

import com.subin.indnews_v1.Constants.ApiPathConstants
import com.subin.indnews_v1.model.response.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FetchArticleApi {
    @GET(ApiPathConstants.getArticles)
    suspend fun getArticles(@Query("q")articleType:String,
                            @Query("from") dateRangeFrom:String,
                            @Query("to") dateRangeTo:String,
                            @Query("sortBy")sortBy:String="popularity",
                             @Query("apiKey")apiKey:String=ApiPathConstants.API_KEY):Response<ArticleResponse>


}