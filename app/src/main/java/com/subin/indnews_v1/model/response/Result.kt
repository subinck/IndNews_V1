package com.subin.indnews_v1.model.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    @SerializedName("category")
    val category: List<String>?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("country")
    val country: List<String>?,
    @SerializedName("creator")
    val creator: List<String>?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("keywords")
    val keywords: List<String>?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("link")
    val link: String?,
    @SerializedName("pubDate")
    val pubDate: String?,
    @SerializedName("source_id")
    val sourceId: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("video_url")
    val videoUrl:String?
) : Parcelable