package com.subin.indnews_v1.repository.GetRepository

import com.subin.indnews_v1.model.response.TechNewsResponse
import retrofit2.Response

interface GetTechNewsRepository {
    suspend fun getTechNewsRepository():Response<TechNewsResponse>
}