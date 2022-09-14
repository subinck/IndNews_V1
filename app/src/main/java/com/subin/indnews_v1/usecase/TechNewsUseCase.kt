package com.subin.indnews_v1.usecase

import com.subin.indnews_v1.model.response.TechNewsResponse
import com.subin.indnews_v1.repository.TechNewsRepository
import com.subin.indnews_v1.usecase.GetUseCase.GetTechNewsUseCase
import retrofit2.Response
import javax.inject.Inject

class TechNewsUseCase @Inject constructor(
    val repository: TechNewsRepository
):GetTechNewsUseCase {
    override suspend fun getTechNews(): Response<TechNewsResponse> {
        return repository.getTechNewsRepository()
    }
}