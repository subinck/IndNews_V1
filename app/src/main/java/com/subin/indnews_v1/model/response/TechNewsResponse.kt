package com.subin.indnews_v1.model.response


import com.google.gson.annotations.SerializedName

data class TechNewsResponse(
    @SerializedName("articles")
    val articles: List<TechNewsArticle>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)