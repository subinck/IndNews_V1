package com.subin.indnews_v1.designs

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.subin.indnews_v1.R
import com.subin.indnews_v1.model.response.LiveNewsArticle

@Composable
fun LiveNewsDetailsScreen(navController: NavController,liveNewsArticle:LiveNewsArticle) {

    val context = LocalContext.current
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    shareDataFromLiveNews(context, liveNewsArticle)
                },
                backgroundColor = colorResource(id = R.color.color_app_theme),
                contentColor = colorResource(id = R.color.white1),
            ) {
                Icon(Icons.Filled.Share, "")
            }
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.smallPadding))
        ) {
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(dimensionResource(id = R.dimen.smallPadding)),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.smallPadding))
            ) {
                Image(
                    painter = rememberImagePainter(liveNewsArticle.urlToImage),
                    contentDescription = "Main Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.twoFiftyDp))
                        .padding(dimensionResource(id = R.dimen.smallPadding))
                )
            }

            liveNewsArticle.title?.let { it1 ->
                Text(
                    text = it1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(dimensionResource(id = R.dimen.smallPadding))
                        .align(Alignment.CenterHorizontally),
                    style = TextStyle(
                        color = colorResource(id = R.color.black2),
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.textPadding)))

            liveNewsArticle.content?.let { it1 ->
                Text(
                    text = it1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(dimensionResource(id = R.dimen.smallPadding)),
                    style = TextStyle(
                        color = colorResource(id = R.color.black2),
                        fontSize = MaterialTheme.typography.subtitle1.fontSize,
                        textAlign = TextAlign.Justify
                    )
                )
            }
        }
    }

}

fun shareDataFromLiveNews(context: Context, article: LiveNewsArticle){
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, article.title)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    ContextCompat.startActivity(context, shareIntent, null)

}
