package com.subin.indnews_v1.repository.GetRepository

import com.subin.indnews_v1.model.response.ArticleResponse
import retrofit2.Response

interface GetArticleRepository {
    suspend fun getArticleRepository(articleType:String,dateRange:String,dateRangeTo:String): Response<ArticleResponse>
}