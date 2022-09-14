package com.subin.indnews_v1.usecase.GetUseCase

import com.subin.indnews_v1.model.response.ArticleResponse
import retrofit2.Response

interface GetArticleUseCase {
    suspend operator  fun invoke(articleType:String,dateRange:String,dateRangeTo:String): Response<ArticleResponse>
}