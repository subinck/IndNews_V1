package com.subin.indnews_v1.repository

import com.subin.indnews_v1.api.FetchLiveNewsApi
import com.subin.indnews_v1.model.response.LiveNewsResponse
import com.subin.indnews_v1.repository.GetRepository.GetLiveNewsRepository
import retrofit2.Response
import javax.inject.Inject

class LiveNewsRepository @Inject constructor(val api: FetchLiveNewsApi):GetLiveNewsRepository {
    override suspend fun getLiveNewsRepository(): Response<LiveNewsResponse> {
      return api.getLiveNews()
    }
}