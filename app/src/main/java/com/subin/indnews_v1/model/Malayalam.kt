package com.subin.indnews_v1.model


import com.google.gson.annotations.SerializedName

data class Malayalam(

    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("youtube_link")
    val youtubeLink: String
)