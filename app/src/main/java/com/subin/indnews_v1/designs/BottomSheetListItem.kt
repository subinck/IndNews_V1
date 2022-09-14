package com.subin.indnews_v1.designs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.subin.indnews_v1.R

@Composable
fun BottomSheetListItem(icon: Int, title: String, onItemClick: (String) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(title) })
            .height(150.dp)
            .background(color = colorResource(id = R.color.color_app_theme))
            .padding(start = 15.dp, bottom = 6.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = icon),
            contentDescription = "Share", tint = Color.White,
        modifier = Modifier.size(30.dp))
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = title, color = Color.White)
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetListItemPreview() {
    BottomSheetListItem(icon = R.drawable.ic_filter_list, title = "Share", onItemClick = { })
}

@Composable
fun BottomSheetContent(onItemClick:(String)->Unit) {
    val context = LocalContext.current
    Column {

        Icon(
            modifier=Modifier
                .padding(8.dp)
                .size(20.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.ic_drag_handle),
            contentDescription = "drag", tint = Color.White,
            )

        BottomSheetListItem(
            icon = R.drawable.apple,
            title = "Apple",
            onItemClick = { title ->
             onItemClick(title)
            })
        Divider(
            modifier = Modifier
                .padding(1.dp)
                .background(colorResource(id = R.color.white1)),
        )
        BottomSheetListItem(
            icon = R.drawable.tesla,
            title = "Tesla",
            onItemClick = { title ->
               onItemClick(title)
            })
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetContentPreview() {
    BottomSheetContent(){}
}