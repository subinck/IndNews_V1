package com.subin.indnews_v1.repository

import com.subin.indnews_v1.api.FetchArticleApi
import com.subin.indnews_v1.model.response.ArticleResponse
import com.subin.indnews_v1.repository.GetRepository.GetArticleRepository
import retrofit2.Response
import javax.inject.Inject

class ArticleRepository @Inject constructor(
   private val api:FetchArticleApi
):GetArticleRepository {
    override suspend fun getArticleRepository(articleType: String, dateRangeFrom: String,dateRangeTo:String):Response<ArticleResponse> {
       return api.getArticles(articleType = articleType,dateRangeFrom =dateRangeFrom,dateRangeTo=dateRangeTo) }
    }
