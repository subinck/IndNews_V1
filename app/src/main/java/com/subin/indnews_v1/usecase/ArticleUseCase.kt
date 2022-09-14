package com.subin.indnews_v1.usecase

import com.subin.indnews_v1.model.response.ArticleResponse
import com.subin.indnews_v1.repository.ArticleRepository
import com.subin.indnews_v1.usecase.GetUseCase.GetArticleUseCase
import retrofit2.Response
import javax.inject.Inject

class ArticleUseCase @Inject constructor(
    private val repository: ArticleRepository
):GetArticleUseCase {

    override suspend fun invoke(articleType: String, dateRange: String,dateRangeTo:String): Response<ArticleResponse> {
        return repository.getArticleRepository(articleType=articleType,dateRangeFrom=dateRange, dateRangeTo = dateRangeTo)
    }

}