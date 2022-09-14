package com.subin.indnews_v1.repository.GetRepository

import com.subin.indnews_v1.model.response.BreakingNews
import retrofit2.Response

interface GetBreakingNewsRepository {

    suspend fun getBreakingNewsRepository(pageCount:Int):Response<BreakingNews>

}