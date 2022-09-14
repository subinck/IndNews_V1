package com.subin.indnews_v1.model.response


import com.google.gson.annotations.SerializedName

data class LiveNewsResponse(
    @SerializedName("articles")
    val articles: List<LiveNewsArticle>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)