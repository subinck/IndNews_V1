package com.subin.indnews_v1.model


import com.google.gson.annotations.SerializedName

data class LiveChannels(
    @SerializedName("LiveChannels")
    val liveChannels: LiveChannelsX
)