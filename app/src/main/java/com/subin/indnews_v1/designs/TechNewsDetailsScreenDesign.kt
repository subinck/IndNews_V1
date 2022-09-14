package com.subin.indnews_v1.designs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.subin.indnews_v1.R
import com.subin.indnews_v1.model.response.TechNewsArticle

@Composable
fun TechNewsDetailsScreen(navController: NavController, att: TechNewsArticle){

    Column(
        modifier = Modifier.padding(top = 60.dp)

    ) {
        Card(
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Image(painter = rememberImagePainter(att.urlToImage),
                contentDescription ="Main Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(8.dp)
            )
        }

        Text(text =att.title ,
            modifier= Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            style = TextStyle(color = colorResource(id = R.color.black2),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold)

        )
        Spacer(modifier = Modifier.size(20.dp))

        Text(text =att.content ,
            modifier= Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            style = TextStyle(color = colorResource(id = R.color.black2),
                fontSize = 16.sp)
        )
    }
}