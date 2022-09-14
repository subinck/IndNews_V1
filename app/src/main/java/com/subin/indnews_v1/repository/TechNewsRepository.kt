package com.subin.indnews_v1.repository

import com.subin.indnews_v1.api.FetchTechNewsApi
import com.subin.indnews_v1.model.response.TechNewsResponse
import com.subin.indnews_v1.repository.GetRepository.GetTechNewsRepository
import retrofit2.Response
import javax.inject.Inject


class TechNewsRepository @Inject constructor(  val api: FetchTechNewsApi):GetTechNewsRepository {
    override suspend fun getTechNewsRepository(): Response<TechNewsResponse> {
      return api.fetchNewsApi()
    }
}