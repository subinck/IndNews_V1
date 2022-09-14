package com.subin.indnews_v1.model.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LiveNewsSource(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
):Parcelable