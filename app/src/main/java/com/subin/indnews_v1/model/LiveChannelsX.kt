package com.subin.indnews_v1.model


import com.google.gson.annotations.SerializedName

data class LiveChannelsX(
    @SerializedName("id")
    val id:String,
    @SerializedName("Malayalam")
    val malayalam: List<Malayalam>
)