package com.subin.indnews_v1.Utility.Toast

import androidx.compose.ui.graphics.Color
import com.subin.indnews_v1.R

class Error:CustomToastProperty {
    override fun getResourceId(): Int {
      return R.drawable.ic__error
    }

    override fun getBackgroundColor(): Color {
     return Color.Red
    }
}