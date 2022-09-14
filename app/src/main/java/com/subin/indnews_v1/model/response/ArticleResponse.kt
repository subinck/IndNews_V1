package com.subin.indnews_v1.model.response


import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Long
)