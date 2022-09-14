package com.subin.indnews_v1.Utility.Toast

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object CustomToastUtil {
    @Composable
    fun ToastSuccess(
        message: String,
        duration: Int = Toast.LENGTH_LONG,
        padding: PaddingValues = PaddingValues(0.dp),
        contentAlignment: Alignment = Alignment.BottomCenter
    ) {
        val sSuccessToast = CustomToast(LocalContext.current)
        sSuccessToast.MakeToast(
            message = message,
            duration = duration,
            type = Success(),
            padding = padding,
            contentAlignment = contentAlignment
        )
       sSuccessToast.show()
    }


    @Composable
    fun ToastError(
        message: String,
        duration: Int = Toast.LENGTH_LONG,
        padding: PaddingValues = PaddingValues(0.dp),
        contentAlignment: Alignment = Alignment.BottomCenter
    ) {
        val sSuccessToast = CustomToast(LocalContext.current)
        sSuccessToast.MakeToast(
            message = message,
            duration = duration,
            type = Error(),
            padding = padding,
            contentAlignment = contentAlignment
        )
        sSuccessToast.show()
    }

    @Composable
    fun SetView(
        messageTxt: String,
        resourceIcon: Int,
        backgroundColor: Color,
        padding: PaddingValues,
        contentAlignment: Alignment
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = contentAlignment
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentSize(),
                color = Color.Transparent
            ) {
                Row(
                    modifier = Modifier
                        .background(
                            color = backgroundColor,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp),
                        painter = painterResource(id = resourceIcon),
                        contentDescription = "",
                    )
                    Text(
                        text = messageTxt,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        style = TextStyle(color = Color.White),
                        modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp)
                    )
                }
            }
        }
    }
}