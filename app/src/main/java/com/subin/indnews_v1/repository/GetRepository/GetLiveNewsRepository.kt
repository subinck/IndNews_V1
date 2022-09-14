package com.subin.indnews_v1.repository.GetRepository

import com.subin.indnews_v1.model.response.LiveNewsResponse
import retrofit2.Response

interface GetLiveNewsRepository {
    suspend fun getLiveNewsRepository(): Response<LiveNewsResponse>
}