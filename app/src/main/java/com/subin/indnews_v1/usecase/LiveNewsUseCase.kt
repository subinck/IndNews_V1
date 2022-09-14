package com.subin.indnews_v1.usecase

import com.subin.indnews_v1.model.response.LiveNewsResponse
import com.subin.indnews_v1.repository.LiveNewsRepository
import com.subin.indnews_v1.usecase.GetUseCase.GetLiveNewsUseCase
import retrofit2.Response
import javax.inject.Inject

class LiveNewsUseCase @Inject constructor( private  val repo:LiveNewsRepository):GetLiveNewsUseCase {
    override suspend fun invoke(): Response<LiveNewsResponse> {
        return repo.getLiveNewsRepository()
    }

}