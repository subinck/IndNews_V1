package com.subin.indnews_v1.usecase.GetUseCase

import com.subin.indnews_v1.model.response.LiveNewsResponse
import retrofit2.Response

interface GetLiveNewsUseCase {
    suspend operator  fun invoke(): Response<LiveNewsResponse>
}