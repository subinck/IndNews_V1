package com.subin.indnews_v1.designs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.subin.indnews_v1.NavItem
import com.subin.indnews_v1.R
import com.subin.indnews_v1.model.response.TechNewsArticle
import com.subin.indnews_v1.viewmodel.TechNewsViewModel
import com.subin.indnews_v1.viewmodel.TechNewsViewStates
import kotlinx.coroutines.delay

@Composable
fun TechNewsScreen(
    navController: NavController,
    viewModel: TechNewsViewModel= hiltViewModel()

){

    DisposableEffect(key1 = Unit) {
            viewModel.getTechNews()
        onDispose { }
    }
    val viewState by remember { viewModel.techNewsDetails }
    val scrollState = rememberLazyListState()
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)
    Column(
        modifier =
        Modifier.fillMaxSize()
    ) {

        when (viewState) {
            is TechNewsViewStates.Success -> {
                Scaffold { paddingValues ->
                    SwipeRefresh(
                        state = swipeRefreshState,
                        onRefresh = {
                            isRefreshing = true
                            viewModel.getTechNews()
                        },
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 50.dp),
                            state = scrollState,
                            contentPadding = paddingValues

                        ) {
                            items((viewState as TechNewsViewStates.Success).data) { items ->
                                SingleTechNewsItem(items, navController = navController)
                            }
                        }
                    }
                }
            }
            is TechNewsViewStates.Error -> {
                Text(
                    text = (viewState as TechNewsViewStates.Error).errorMessage!!,
                    modifier = Modifier
                        .padding(6.dp)
                        .align(Alignment.CenterHorizontally),
                    style = TextStyle(
                        color = colorResource(id = R.color.black2),
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            else -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                }

            }
        }
    }
    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(1000L)
            isRefreshing= false
        }
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SingleTechNewsItem(listArticle: TechNewsArticle,navController: NavController) {
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(6.dp)
                .clickable { },
            elevation = 10.dp,
            shape = RoundedCornerShape(6.dp),
            onClick = {

                navController.currentBackStackEntry?.savedStateHandle?.set("TechNews",listArticle)
                navController.navigate(NavItem.TechNewsDetailsScreen.route)
            }

        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = colorResource(id = R.color.white1)),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .weight(2F)
                        .padding(12.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = 8.dp
                ) {

                    Image(
                        painter = if (listArticle.urlToImage != null) {
                            rememberImagePainter(
                                listArticle.urlToImage
                            )
                        } else {
                            painterResource(id = R.drawable.ic_broken_image)
                        },
                        contentDescription = "Category Image",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(6.dp)
                            .clip(shape = RoundedCornerShape(4.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Text(
                    text = listArticle.title,
                    style= MaterialTheme.typography.h4,
                    modifier = Modifier
                        .weight(2F)
                        .padding(6.dp)
                    ,
                    textAlign = TextAlign.Left,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

            }
        }

    }
}

