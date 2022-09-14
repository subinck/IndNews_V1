package com.subin.indnews_v1.Utility.Toast

import androidx.compose.ui.graphics.Color

interface CustomToastProperty {
    fun getResourceId(): Int
    fun getBackgroundColor(): Color
}