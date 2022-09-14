package com.subin.indnews_v1.Utility.Toast

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.savedstate.ViewTreeSavedStateRegistryOwner

class CustomToast(context: Context) : Toast(context) {

    @Composable
    fun MakeToast(
        message: String,
        duration: Int = LENGTH_LONG,
        type: CustomToastProperty,
        padding: PaddingValues,
        contentAlignment: Alignment
    ) {
        val context = LocalContext.current

        val views = ComposeView(context)
        views.setContent {
            CustomToastUtil.SetView(
                messageTxt = message,
                resourceIcon = type.getResourceId(),
                backgroundColor = type.getBackgroundColor(),
                padding = padding,
                contentAlignment = contentAlignment
            )
        }

        ViewTreeLifecycleOwner.set(views, LocalLifecycleOwner.current)
        ViewTreeViewModelStoreOwner.set(views, LocalViewModelStoreOwner.current)
        ViewTreeSavedStateRegistryOwner.set(views, LocalSavedStateRegistryOwner.current)

        this.duration = duration
        this.view = views
    }
}