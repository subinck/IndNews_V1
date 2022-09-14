package com.subin.indnews_v1.designs

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.subin.indnews_v1.Constants.PrefConstants
import com.subin.indnews_v1.Utility.Utils
import com.subin.indnews_v1.R
import com.subin.indnews_v1.SharedPreference.IndNewsSharedPref
import com.subin.indnews_v1.model.CategoryItems
import com.subin.indnews_v1.viewmodel.CategoryScreenViewModel
import com.subin.indnews_v1.viewmodel.CategoryViewState

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun CategoryScreen(viewModel: CategoryScreenViewModel= hiltViewModel(),onClickButton:(Unit)->Unit){
    val context= LocalContext.current
    val list= Utils.getCategory(context)
    val category=list.Category.CategoryItems
    val selectedItems:ArrayList<CategoryItems> =ArrayList()
    val preference:IndNewsSharedPref= IndNewsSharedPref(context)

    var onSelect by  remember { mutableStateOf(
        category.map {
            CategoryItems(it.name,it.image,it.isSelected)
        }
    ) }

   val showDialog = remember { mutableStateOf(false) }
      val state by remember {viewModel.categoryScreenStatus}
    when(state) {

        is CategoryViewState.Success -> {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            preference.setIsSetCategoryItems(PrefConstants.IS_SET_CATEGORY_ITEMS,true)
        }

        is CategoryViewState.Error -> {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
        }

        else -> {
            CategoryViewState.Loading
        }
    }
    Column( modifier = Modifier
        .statusBarsPadding()
        .navigationBarsWithImePadding()
        .fillMaxSize()) {
        Box(
           modifier = Modifier
               .fillMaxWidth()
               .height(dimensionResource(id = R.dimen.appBarHeight))
               .background(
                   colorResource(id = R.color.color_status_bar)

               )
        ){
            Text(text ="Category",

                 style = TextStyle(color = colorResource(id = R.color.white1),
                 fontWeight = FontWeight.Bold,
                  fontSize =MaterialTheme.typography.h6.fontSize
                 ),
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.tinyPadding))
                    .align(Alignment.Center)
            )
        }
        LazyVerticalGrid(cells= GridCells.Adaptive(minSize = dimensionResource(id = R.dimen.oneFiftyDp)),

            // content padding
            contentPadding = PaddingValues(
                start = dimensionResource(id = R.dimen.mediumPadding),
                top = dimensionResource(id = R.dimen.largePadding),
                end =  dimensionResource(id = R.dimen.mediumPadding),
                bottom =  dimensionResource(id = R.dimen.largePadding)
            ), content = {

                items(onSelect.size) { index ->
                    Card(
                        backgroundColor = Color.White,
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.smallPadding))
                            .fillMaxWidth(),
                        elevation =dimensionResource(id = R.dimen.smallPadding),
                    ) {
                        Box(
                        ) {
                            Card(
                                backgroundColor = Color.Red,
                                modifier = Modifier
                                    .padding(dimensionResource(id = R.dimen.smallPadding))
                                    .fillMaxWidth(),
                                elevation =dimensionResource(id = R.dimen.smallPadding),
                                onClick =
                                {
                                    onSelect = onSelect.mapIndexed { j, categoryItems ->
                                        if (index == j) {
                                                selectedItems.add(categoryItems)
                                            categoryItems.copy(isSelected = !categoryItems.isSelected)
                                        } else categoryItems
                                    }

                                }
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize()
                                ) {

                                    Card(
                                        backgroundColor = Color.White,
                                        modifier = Modifier
                                            .padding(dimensionResource(id = R.dimen.smallPadding))
                                            .fillMaxWidth(),
                                        elevation =dimensionResource(id = R.dimen.smallPadding),
                                    ) {
                                        Image(
                                            painter = rememberImagePainter(onSelect[index].image),
                                            contentDescription = "image",
                                            modifier = Modifier
                                                .width(dimensionResource(id = R.dimen.categoryImageWidth))
                                                .height(dimensionResource(id = R.dimen.categoryImageHeight))
                                                .padding(dimensionResource(id = R.dimen.tinyPadding))
                                                .clip(
                                                    shape = RoundedCornerShape(
                                                        dimensionResource(
                                                            id = R.dimen.fourDp
                                                        )
                                                    )
                                                )
                                                .align(Alignment.CenterHorizontally),
                                            contentScale = ContentScale.Crop)

                                    }

                                    Text(
                                        text = onSelect[index].name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = MaterialTheme.typography.h6.fontSize,
                                        color = Color(0xFFFFFFFF),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .padding(dimensionResource(id = R.dimen.largePadding))
                                            .align(Alignment.CenterHorizontally))
                                }
                            }

                            if (onSelect[index].isSelected) {
                              Icon(painter = painterResource(id = R.drawable.selected),
                               contentDescription = "selected",
                                 modifier = Modifier
                                     .padding(dimensionResource(id = R.dimen.smallPadding))
                                     .size(dimensionResource(id = R.dimen.defaultIconSize))
                                     .align(Alignment.BottomEnd),
                                 tint = Color.Green)
                            }
                        }
                    }
                }
            }
        )

        Button(
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.appBarHeight))
                .background(colorResource(id = R.color.color_app_theme))
                .height(dimensionResource(id = R.dimen.appBarHeight))
                .width(dimensionResource(id = R.dimen.oneFiftyDp))
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.mediumPadding)),
            onClick = {
               if (selectedItems.size<3){
                    showDialog.value=true
                      for(item in onSelect) {
                       item.isSelected = false

                       }
               }else{
                 viewModel.insertCategoryItems(selectedItems)
                   onClickButton(Unit)
               }
                
            }) {
            Text(text = "Continue")
        }
    }
    if (showDialog.value){
      Alert(showDialog)
    }

}

@Composable
fun Alert(showDialog:MutableState<Boolean>) {
    AlertDialog(
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.normalAlertBoxHeight))
            .width(dimensionResource(id = R.dimen.normalAlertBoxWidth)),
        title = {
            Text(text = "Alert")
        },
        text = {
            Text("Please Select At Least 3 Category ")
        },
        onDismissRequest = {

        },

            buttons = {
                Box(modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()) {
                    Button(
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.smallPadding))
                            .align(Alignment.CenterEnd),
                        onClick = { showDialog.value = false }) {
                        Text("OK")
                    }
                }
            })
}

