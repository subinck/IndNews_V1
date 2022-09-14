package com.subin.indnews_v1.designs

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.ui.graphics.Color
import com.subin.indnews_v1.R

@Composable
fun ProgressBarDesign(){
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
//        CircularProgressIndicator(
//            // progress = 0.45f,
//            color = colorResource(id = R.color.black),
//            modifier = Modifier.then(Modifier.size(60.dp))
//        )
//        CircularProgressIndicator(
//            //  progress = 0.55f,
//            color = colorResource(id = R.color.color_app_theme),
//            modifier = Modifier.then(Modifier.size(80.dp))
//        )

        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp),
            backgroundColor = Color.White,
            color = colorResource(id = R.color.color_app_theme) //progress color
        )
    }
}