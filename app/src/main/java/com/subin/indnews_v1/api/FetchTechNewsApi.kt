package com.subin.indnews_v1.api

import com.subin.indnews_v1.Constants.ApiPathConstants
import com.subin.indnews_v1.model.response.TechNewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FetchTechNewsApi {
    @GET(ApiPathConstants.getTechNews)
  suspend  fun fetchNewsApi(
        @Query("sources") source:String="techcrunch",
         @Query("apiKey") apiKey:String=ApiPathConstants.API_KEY
    ):Response<TechNewsResponse>


}