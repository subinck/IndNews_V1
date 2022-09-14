package com.subin.indnews_v1.usecase.GetUseCase

import com.subin.indnews_v1.model.response.TechNewsResponse
import retrofit2.Response

interface GetTechNewsUseCase {
    suspend fun getTechNews():Response<TechNewsResponse>
}