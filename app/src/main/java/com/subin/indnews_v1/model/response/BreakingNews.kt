package com.subin.indnews_v1.model.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BreakingNews(
    @SerializedName("nextPage")
    val nextPage: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
) : Parcelable