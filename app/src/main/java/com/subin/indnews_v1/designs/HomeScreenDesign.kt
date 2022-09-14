package com.subin.indnews_v1.designs

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.compose.animation.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.subin.indnews_v1.NavItem
import com.subin.indnews_v1.R
import com.subin.indnews_v1.activity.LiveNewsActivity
import com.subin.indnews_v1.ui.theme.Purple40
import com.subin.indnews_v1.viewmodel.HomeScreenViewModel
import com.subin.indnews_v1.viewmodel.RegistrationViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreenDesign(
    navController: NavController,
viewModel: HomeScreenViewModel= hiltViewModel()
) {
    val context= LocalContext.current
    val tabItems = listOf("Ind-News", "TechCrunch", "Article")
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val _interactionSource = remember { MutableInteractionSource() }

    DisposableEffect(Unit){
        viewModel.getProfileImage()

        onDispose {  }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .fillMaxSize()
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.appBarHeight))
                .background(colorResource(id = R.color.color_status_bar))) {

                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Cursive)) {
                        append("I")
                    }
                    withStyle(style = SpanStyle(color = Color.White,fontWeight = FontWeight.Bold)) {
                        append("nd")
                    }

                    withStyle(style = SpanStyle(color = Color.Black,fontWeight = FontWeight.Bold,fontFamily = FontFamily.Cursive)) {
                        append(" N")
                    }
                    withStyle(style = SpanStyle(color = Color.White,fontWeight = FontWeight.Bold)) {
                        append("ews")
                    }
                },
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    modifier = Modifier.align(Alignment.Center))

                 if(viewModel.bitmapFile.value!=null){
                     Image(bitmap = viewModel.bitmapFile.value!!.asImageBitmap(), contentDescription ="profile",
                          contentScale = ContentScale.Crop,
                          modifier = Modifier
                              .padding(
                                  start = dimensionResource(id = R.dimen.smallPadding),
                                  end = dimensionResource(id = R.dimen.twentyDp)
                              )
                             .size(dimensionResource(id = R.dimen.thirtyDp))
                             .clip(shape = CircleShape)
                              .border(4.dp,MaterialTheme.colors.surface, CircleShape)
                              .align(Alignment.CenterEnd)
                             .clickable {
                                 navController.navigate(NavItem.ProfileScreen.route)
                             }
                     )
                 }else{
                     Image(painter = painterResource(id = R.drawable.userpn), contentDescription ="profile",
                         contentScale = ContentScale.Crop,
                         modifier = Modifier
                             .padding(
                                 start = dimensionResource(id = R.dimen.smallPadding),
                                 end = dimensionResource(id = R.dimen.twentyDp)
                             )
                             .size(dimensionResource(id = R.dimen.thirtyDp))
                             .clip(shape = CircleShape)
                             .border(4.dp, MaterialTheme.colors.surface, CircleShape)
                             .align(Alignment.CenterEnd)
                             .clickable {
                                 navController.navigate(NavItem.ProfileScreen.route) }
                     )
                 }
            }

            Box(
                modifier = Modifier.wrapContentSize()
            ) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    backgroundColor = Color.Transparent,
                    modifier = Modifier
                        .padding(all = 10.dp)
                        .wrapContentSize()
                        .align(Alignment.Center)
                        .background(color = MaterialTheme.colors.surface)
                        .clip(RoundedCornerShape(30.dp))
                         .clickable( indication = null,
                             interactionSource = remember { MutableInteractionSource() }){},
                           indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                                 Modifier
                                .pagerTabIndicatorOffset(
                                    pagerState, tabPositions)
                                .width(0.dp)
                                .height(0.dp),) }) {
                          tabItems.forEachIndexed { index, title ->
                          val color = remember {
                            Animatable(Purple40)
                        }
                        LaunchedEffect(
                            pagerState.currentPage == index
                        ) {
                            color.animateTo(
                                if (pagerState.currentPage == index) Color.White else Color.Red
                            )
                        }
                        Tab(
                            text = {
                                Text(
                                    title,
                                    style = if (pagerState.currentPage == index) TextStyle(
                                        color = Purple40,
                                        fontSize = 16.sp
                                    ) else TextStyle(color = Color.White, fontSize = 14.sp),
                                )
                            },
                            selected = pagerState.currentPage == index,
                            modifier = Modifier.background(color = color.value, shape = RoundedCornerShape(30.dp))
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() } // This is mandatory
                                ){},
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)

                                }
                            },
                           // interactionSource = _interactionSource,
                        )
                    }

                }
            }
            HorizontalPager(
                count = tabItems.size,
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color =MaterialTheme.colors.surface)
            ) { page ->

                when (page) {
                    0 -> LiveNewsDesign(navController = navController)
                    1 -> TechNewsScreen(navController = navController)
                    2 ->ArticleScreenDesign(navController = navController)
                    //moveToLiveScreenActivity(context =context )
                    else -> "Inavalid Page"
                }
            }
        }

    }
}

fun moveToLiveScreenActivity(context: Context){
    context.startActivity(Intent(context, LiveNewsActivity::class.java))
}


@Composable
@Preview(showBackground = true)
fun HomePreView() {
    val navController = rememberNavController()
    HomeScreenDesign(navController = navController)
}


