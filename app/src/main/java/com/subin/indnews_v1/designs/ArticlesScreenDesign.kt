package com.subin.indnews_v1.designs

import android.annotation.SuppressLint
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.subin.indnews_v1.Constants.PrefConstants
import com.subin.indnews_v1.NavItem
import com.subin.indnews_v1.R
import com.subin.indnews_v1.SharedPreference.IndNewsSharedPref
import com.subin.indnews_v1.model.response.Article
import com.subin.indnews_v1.viewmodel.ArticleScreenViewModel
import com.subin.indnews_v1.viewmodel.ArticleViewStates
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
    fun ArticleScreenDesign(
    navController: NavController,
        viewModel: ArticleScreenViewModel = hiltViewModel(),

    ) {
    val appPreference:IndNewsSharedPref= IndNewsSharedPref(LocalContext.current)
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed))
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    val df = SimpleDateFormat( " yyyy-MM-dd")
    val c = Calendar.getInstance()
    val formattedDate: String = df.format(c.time)
    val aType=appPreference.getArticleType(PrefConstants.ARTICLE_TYPE)
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)


    DisposableEffect(key1 = Unit) {
        if (aType.isNotEmpty()){
            viewModel.getArticles(aType, formattedDate, formattedDate)
        }else{
            viewModel.getArticles("Apple", formattedDate, formattedDate)
        }
        onDispose { }
    }
    val viewState by remember { viewModel.articleDetails }
    val scrollState = rememberLazyListState()
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            BottomSheetContent(){item->
                if (item == "Apple"){
                    viewModel.getArticles("Apple",formattedDate,formattedDate)
                    appPreference.setArticleType(PrefConstants.ARTICLE_TYPE,"Apple")
                }else{
                    viewModel.getArticles("Tesla",formattedDate,formattedDate)
                    appPreference.setArticleType(PrefConstants.ARTICLE_TYPE,"Tesla")
                }

            }
        },
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetPeekHeight = 0.dp,
        sheetBackgroundColor = colorResource(id = R.color.color_app_theme),
    ) {
        Box(
            Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()

            ) {

                when (val state = viewState) {
                    is ArticleViewStates.Success -> {
                        Scaffold { paddingValues ->
                            SwipeRefresh(
                                state = swipeRefreshState,
                                onRefresh = {
                                    isRefreshing = true
                                    viewModel.getArticles("Apple", formattedDate, formattedDate)
                                },
                            ) {

                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(bottom = dimensionResource(id = R.dimen.appBarHeight)),
                                    state = scrollState,
                                    contentPadding = paddingValues

                                ) {
                                    items(state.data) { items ->
                                        SingleArticleItem(items, navController)
                                    }
                                }
                            }
                        }
                    }
                    is ArticleViewStates.Error -> {
                        Text(
                            text = state.errorMessage!!,
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
            Column(
                modifier = Modifier

                    .fillMaxWidth()
                    .wrapContentHeight()
                    .align(Alignment.BottomCenter),

                ) {
                BottomBar(viewModel,appPreference){
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        } else {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    }
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
fun SingleArticleItem(listArticle:Article,navController: NavController) {
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

                navController.currentBackStackEntry?.savedStateHandle?.set("Article",listArticle)
                navController.navigate(NavItem.ArticleDetailsScreen.route)
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

@SuppressLint("SimpleDateFormat")
@Composable
fun BottomBar(viewModel: ArticleScreenViewModel,appPreference: IndNewsSharedPref,onClick:(Unit)->Unit) {
    val df = SimpleDateFormat("EEEE , dd-MMMM-yyyy")
    val newDf = SimpleDateFormat(" yyyy-MM-dd")
    val c = Calendar.getInstance()
    val c1 = Calendar.getInstance()
    val formattedDate: String = df.format(c.time)
    var currentDates: String by remember { mutableStateOf(formattedDate) }
    val dateChanged = remember { mutableStateOf(false) }
    val backDatePressed = remember { mutableStateOf(false) }
    val forwardDatePressed = remember { mutableStateOf(false) }
     val argType=appPreference.getArticleType(PrefConstants.ARTICLE_TYPE)

     Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.color_app_theme))
            .height(dimensionResource(id = R.dimen.appBarHeight))
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.appBarHeight))
                .background(colorResource(id = R.color.color_app_theme))
                .weight(1f),
        ) {


            IconButton(modifier = Modifier
                .padding(
                    dimensionResource(id = R.dimen.tinyPadding), vertical = dimensionResource(
                        id = R.dimen.tenDp
                    )
                )
                .align(Alignment.CenterStart)
                .size(dimensionResource(id = R.dimen.defaultIconSize)),
                onClick = {
                    currentDates = minusDate(currentDates)
                    dateChanged.value = true
                    backDatePressed.value = true

                }) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = "Arrow Left",
                    tint = colorResource(id = R.color.white1),
                    modifier = Modifier.size(dimensionResource(id = R.dimen.fourtiFiveDp))
                )
            }
            if (dateChanged.value) {
                Text(
                    text = currentDates,
                    modifier = Modifier
                        .align(Alignment.Center),
                    style = TextStyle(color = colorResource(id = R.color.white1)),
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize
                )

                dateChanged.value = false

            }

            Text(
                text = currentDates,
                modifier = Modifier
                    .align(Alignment.Center),
                style = TextStyle(color = colorResource(id = R.color.white1)),
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            )
            //    if (!currentDates.equals(Utils.getCurrentDate())) {
            IconButton(modifier = Modifier
                .padding(
                    dimensionResource(id = R.dimen.tinyPadding), vertical = dimensionResource(id = R.dimen.tenDp)
                )
                .align(Alignment.CenterEnd)
                .size(dimensionResource(id = R.dimen.sixtyDp)),
                onClick = {
                    currentDates = addDate(currentDates)
                    dateChanged.value = true
                    forwardDatePressed.value = true
                }) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = "Arrow Right",
                    tint = colorResource(id = R.color.white1),
                    modifier = Modifier.size(dimensionResource(id = R.dimen.fourtiFiveDp))
                )
            }
            //  }
        }

        if (backDatePressed.value || forwardDatePressed.value) {
            val date: Date = df.parse(currentDates)!!
            val formattedDate: String = newDf.format(date)

            performGetArticles(formattedDate, viewModel,argType)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.color_app_theme))
                .height(dimensionResource(id = R.dimen.appBarHeight))
                .weight(0.3f),
        ) {
                IconButton(modifier = Modifier
                    .padding(
                        dimensionResource(id = R.dimen.tinyPadding), vertical = dimensionResource(
                            id = R.dimen.tenDp
                        )
                    )
                    .align(Alignment.Center)
                    .size(dimensionResource(id = R.dimen.sixtyDp)),
                    onClick = {
                        onClick(Unit)
                    }) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter_list),
                        contentDescription = "Filter",
                        tint = colorResource(id = R.color.white1),
                        modifier = Modifier.size(dimensionResource(id = R.dimen.fourtiFiveDp))
                    )
                }
            }
    }
}
fun addDate(dateInString: String): String {
    val sdf = SimpleDateFormat("EEEE , dd-MMMM-yyyy")
    val c = Calendar.getInstance()
    c.time = sdf.parse(dateInString)

    var dt = Date()
    c.add(Calendar.DATE, 1)
    dt = c.time
    return sdf.format(dt)

}

fun minusDate(dateInString: String): String {
    val sdf = SimpleDateFormat("EEEE , dd-MMMM-yyyy")
    val c = Calendar.getInstance()
    c.time = sdf.parse(dateInString)

    var dt = Date()
    c.add(Calendar.DATE, -1)
    dt = c.time
    return sdf.format(dt)

}
fun  performGetArticles(currentDates:String,
                        viewModel: ArticleScreenViewModel,type:String){
     viewModel.getArticles(type,currentDates,currentDates)
}



@Preview(showBackground = true)
@Composable
fun ArticlePreview(){
    val viewModel:ArticleScreenViewModel= hiltViewModel()
    val context= LocalContext.current
    val pref=IndNewsSharedPref(context)
   BottomBar(viewModel,pref){}
}

