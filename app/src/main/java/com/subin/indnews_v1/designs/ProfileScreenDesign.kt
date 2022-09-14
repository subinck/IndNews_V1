package com.subin.indnews_v1.designs

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.subin.indnews_v1.Constants.PrefConstants
import com.subin.indnews_v1.R
import com.subin.indnews_v1.SharedPreference.IndNewsSharedPref
import com.subin.indnews_v1.viewmodel.ProfileScreenViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProfileScreenDesign(navController: NavController,
activity: Activity,
viewModel: ProfileScreenViewModel= hiltViewModel()){
    val (focusRequester) = FocusRequester.createRefs()
    val name = remember { mutableStateOf(TextFieldValue()) }
    val nameErrorState = remember { mutableStateOf(false) }
    val email = remember { mutableStateOf(TextFieldValue()) }
    val emailErrorState = remember { mutableStateOf(false) }
    val phone = remember { mutableStateOf(TextFieldValue()) }
    var state:String?=null
    val phoneErrorState = remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap =  remember { mutableStateOf<Bitmap?>(null) }
    val bi =  remember { mutableStateOf<Bitmap?>(null) }
    val bitmapState =  remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? -> imageUri = uri }
    var filePath:Uri?=null
    val preference=IndNewsSharedPref(context)
    val bitMapSuccess= remember { mutableStateOf(false)}

    val user=preference.getUserDetails(PrefConstants.USER_DETAILS)
    if(user.email!=null){
        email.value=TextFieldValue(user.email)
    }
    if(user.name!=null){
        name.value=TextFieldValue(user.name!!)
    }
    if (user.phone!=null){
        phone.value=TextFieldValue(user.phone)
    }
    if(user.state!=null){
        state=user.state
    }

    email.value=TextFieldValue(user.email)
    phone.value=TextFieldValue(user.phone)

    ConstraintLayout(
        modifier = Modifier
            .background(Color.Red)
            .wrapContentSize()
    ) {
        val (title, description,logout) = createRefs()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = colorResource(id = R.color.color_app_theme))
                .padding(horizontal = dimensionResource(id = R.dimen.largePadding))
                .clip(shape = RoundedCornerShape(dimensionResource(R.dimen.smallPadding)))
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        ) {
            Box(modifier= Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.appBarHeight))
                .align(Alignment.TopCenter)) {
                IconButton(onClick = {
                                     navController.popBackStack()
                }
                , modifier = Modifier
                        .align(Alignment.TopStart)
                        .size(dimensionResource(id = R.dimen.textPadding))) {
                    Icon(painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription ="back",
                        tint = White )
                }
            }
            ConstraintLayout(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {

                val (image,icon)= createRefs()
                imageUri?.let {
                    if (Build.VERSION.SDK_INT < 28) {
                        bitmap.value = MediaStore.Images
                            .Media.getBitmap(context.contentResolver,it)
                        filePath=it

                    } else {
                        val source = ImageDecoder
                            .createSource(context.contentResolver,it)
                        bitmap.value = ImageDecoder.decodeBitmap(source)
                        filePath=it
                    }
                }
                var bb:Bitmap?=bitmap.value
//                if(viewModel.bitmapLocalDB.value!=null) {
//                    bi.value = viewModel.bitmapLocalDB.value
//                }else{
                    bi.value = viewModel.bitmapFile.value
//                }
                if(bb!=null){
                    bi.value=null
                }
                bitmapState.value = (bitmap.value != null)||(bi.value!=null)

                if(bitmapState.value){
                    bb.let { btm ->
                        bb =bi.value ?: btm
                        Image(
                            bitmap = bb!!.asImageBitmap(),
                            // painter = painterResource(R.drawable.user),
                            contentDescription = "avatar",
                            contentScale = ContentScale.Crop,            // crop the image if it's not a square
                            modifier = Modifier
                                .padding(bottom = dimensionResource(R.dimen.oneTwentyFiveDp))
                                .size(dimensionResource(id = R.dimen.oneFiftyDp))
                                .clip(CircleShape)                 // clip to the circle shape
                                .border(4.dp, Color.White, CircleShape)
                                .constrainAs(image) {
                                    top.linkTo(parent.top, margin = 30.dp)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(parent.bottom)
                                }
                        )
                    }
                }else{
                    Image(
                        //  bitmap = btm.asImageBitmap(),
                        painter = painterResource(R.drawable.user),
                        contentDescription = "avatar",
                        contentScale = ContentScale.Crop,            // crop the image if it's not a square
                        modifier = Modifier
                            .padding(bottom = dimensionResource(R.dimen.oneTwentyFiveDp))
                            .size(dimensionResource(id = R.dimen.oneFiftyDp))
                            .clip(CircleShape)                 // clip to the circle shape
                            .border(4.dp, Color.White, CircleShape)
                            .constrainAs(image) {
                                top.linkTo(parent.top, margin = 30.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            }

                        // add a border (optional)
                    )
                }
                IconButton(onClick = {
                    launcher.launch("image/*")

                },
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.defaultIconSize))
                        .padding(end = dimensionResource(id = R.dimen.mediumPadding))
                        .constrainAs(icon) {
                            top.linkTo(image.top)
                            start.linkTo(image.end)
                        }) {
                    Icon(painter = painterResource(id = R.drawable.ic_edit), contentDescription = "edit button",
                    tint = White)
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.twentyDp)))
                .padding(
                    start = dimensionResource(id = R.dimen.tinyPadding),
                    end = dimensionResource(id = R.dimen.tinyPadding),
                    top = dimensionResource(id = R.dimen.mediumPadding)
                )
                .background(Color.White)
                .constrainAs(description) {
                    top.linkTo(title.bottom, margin = 45.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)

                },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = dimensionResource(id = R.dimen.smallPadding),
                        end = dimensionResource(id = R.dimen.smallPadding)
                    )
            ) {

                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.twentyDp)))

                OutlinedTextField(
                    value = name.value,
                    onValueChange = {
                        if (nameErrorState.value) {
                            nameErrorState.value = false
                        }
                        name.value = it
                    },
                    isError = nameErrorState.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    label = {
                        Text(text = "Enter Name*")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                )
                OutlinedTextField(
                    value = email.value,
                    onValueChange = {
                        if (emailErrorState.value) {
                            emailErrorState.value = false
                        }
                        email.value = it
                    },
                    isError = emailErrorState.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    label = {
                        Text(text = "Enter Email*")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                )

                OutlinedTextField(
                    value = phone.value,
                    onValueChange = {
                        if (phoneErrorState.value) {
                            phoneErrorState.value = false
                        }
                        phone.value = it
                    },
                    isError = phoneErrorState.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    label = {
                        Text(text = "Enter Phone Number*")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                )

               val cName= stateSelection()
                 Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.thirtyDp)))
                  Button(
                    onClick = {
                        when {
                            name.value.text.isEmpty() -> {
                                nameErrorState.value = true
                            }
                            email.value.text.isEmpty() -> {
                                emailErrorState.value = true
                            }
                            phone.value.text.isEmpty() -> {
                                phoneErrorState.value = true
                            }
                            else -> {
                                nameErrorState.value = false
                                emailErrorState.value = false
                                phoneErrorState.value = false
                                if(filePath!=null) {
                                    viewModel.uploadImage(filePath!!)
                                }
                                viewModel.saveOrUpdateProfileDetails(name.value.text,cName,email.value.text,phone.value.text)
                            }
                        }
                    },
                    content = {
                        Text(text = "Save", color = Color.White)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color.Red)
                )
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.twentyDp)))
                Divider(modifier = Modifier
                    .padding(1.dp)
                    .background(
                        colorResource(id = R.color.color_app_theme),
                        RoundedCornerShape(1.dp)
                    ))
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.twentyDp)))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.hundredDp))
                        .background(color = colorResource(id = R.color.white1))
                        .border(1.dp, colorResource(id = R.color.black2), RoundedCornerShape(12.dp))
                        .padding(horizontal = dimensionResource(id = R.dimen.largePadding))
                        .clip(shape = RoundedCornerShape(dimensionResource(R.dimen.smallPadding)))

                ) {
                    Button(
                        onClick = {
                          viewModel.doLogout(activity as FragmentActivity)
                        },
                        content = {
                            Text(text = "Logout", color = Color.White)
                        },
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.CenterStart)
                        ,
                        colors = ButtonDefaults.buttonColors(Color.Red)
                    )
                    Button(
                        onClick = {

                        },
                        content = {
                            Text(text = "Delete Account", color = Color.White)
                        },
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.CenterEnd),
                        colors = ButtonDefaults.buttonColors(Color.Red)
                    )
                }

            }

        }


    }


}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun stateSelection():String {
    val context= LocalContext.current
//    val list=Utils.getStateList(context = context)
//    val l= mutableListOf(list.StateList)
//    val cList= mutableListOf(list.StateList.StateListItem)
//    var cName:MutableState<String>?=null
//    cList.forEachIndexed { index, element ->
//        cName = remember { mutableStateOf<String>(element[index].name)}
//    }

    val countriesList= mutableListOf<String>(
       "AndhraPradesh" ,
       "ArunachalPradesh" ,
       "Assam" ,
       "Bihar" ,
       "Chhattisgarh" ,
       "Goa" ,
       "Gujarat" ,
      "Haryana"  ,
      "HimachalPradesh"  ,
       "Jharkhand" ,
       "Karnataka" ,
       "Kerala" ,
       "MadhyaPradesh" ,
       "Maharashtra" ,
      "Manipur"  ,
      "Meghalaya"  ,
      "Mizoram"  ,
      "Nagaland"  ,
     "Odisha"   ,
      "Punjab"  ,
       "Rajasthan" ,
      "Sikkim"  ,
      "TamilNadu"  ,
       "Telangana" ,
       "Tripura" ,
      "UttarPradesh"  ,
       "Uttarakhand" ,
      "WestBengal"
    )
    var countryName: String by remember { mutableStateOf(countriesList[0]) }
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                readOnly = true,
                value =countryName,
                onValueChange = { },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("State") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                //colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                countriesList.forEach { country ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            countryName=country
                        }
                    ) {
                        Text(text =country.toString())
                    }
                }
            }

        }
    return countryName
    }


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview(){
    val context= LocalContext.current
    val navController:NavController=NavController(context)
    val activity=Activity()
    ProfileScreenDesign(navController,activity)
}