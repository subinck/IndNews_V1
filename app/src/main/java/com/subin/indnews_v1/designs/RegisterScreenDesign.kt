package com.subin.indnews_v1.designs


import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.firebase.firestore.FirebaseFirestore
import com.subin.indnews_v1.Constants.AppConstants
import com.subin.indnews_v1.Constants.PrefConstants
import com.subin.indnews_v1.NavItem
import com.subin.indnews_v1.R
import com.subin.indnews_v1.SharedPreference.IndNewsSharedPref
import com.subin.indnews_v1.Utility.PrefixTransformation
import com.subin.indnews_v1.Utility.Toast.CustomToastUtil
import com.subin.indnews_v1.model.UserDetails
import com.subin.indnews_v1.viewmodel.RegisterViewStates
import com.subin.indnews_v1.viewmodel.RegistrationViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RegisterScreenDesign(navController: NavController,
                         activity: Activity,
                         viewModel: RegistrationViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val email = remember { mutableStateOf(TextFieldValue()) }
    val emailErrorState = remember { mutableStateOf(false) }
    val passwordErrorState = remember { mutableStateOf(false) }
    val confirmPasswordErrorState = remember { mutableStateOf(false) }
     val phoneErrorState = remember { mutableStateOf(false) }
    val passwordMisMatch = remember { mutableStateOf(false) }
    val loginSuccessful = remember { mutableStateOf(0) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val confirmPassword = remember { mutableStateOf(TextFieldValue()) }
    val phoneNumber = remember { mutableStateOf(TextFieldValue()) }
    val mDb: FirebaseFirestore = FirebaseFirestore.getInstance()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val (focusRequester) = FocusRequester.createRefs()
    val pref:IndNewsSharedPref= IndNewsSharedPref(context)
//    val tm :TelephonyManager= context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//    val countryCodeValue: String = tm.networkCountryIso
    val focusManager = LocalFocusManager.current
    val bringFocusRequest = BringIntoViewRequester()
    val otpStatus= remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        println("found activity?$activity")
        val activity1 = activity ?: return@LaunchedEffect
        viewModel.setActivity(activity1)

      }
    val viewState by remember {viewModel.registerDetails}
        Column(
            Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.largePadding))
                .statusBarsPadding()
                .navigationBarsWithImePadding()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
        ) {

            when(val state=viewState){
                is RegisterViewStates.Success ->{
                    if(!otpStatus.value) {
                        otpStatus.value=true
                        navController.currentBackStackEntry?.savedStateHandle?.set("ScreenType",AppConstants.REGISTER_SCREEN)
                        navController.navigate(NavItem.OTPScreen.route)

                    }
                }
                is RegisterViewStates.Error ->{
                    CustomToastUtil.ToastError(
                        message = "${state.errorMessage}!",
                        duration = Toast.LENGTH_LONG,
                        padding = PaddingValues(top = dimensionResource(id = R.dimen.largePadding)),
                        contentAlignment = Alignment.TopCenter
                    )

                }
                is RegisterViewStates.ProgressState-> {
                    if (state.progress == true) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            ProgressBarDesign()
                        }
                    }
                }
                is RegisterViewStates.Loading->{

                }
            }

            Image(
                painter = painterResource(id = R.drawable.logo), contentDescription = "logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .border(
                        dimensionResource(id = R.dimen.tinyBorderSize),
                        color = Color.Red,
                        CircleShape
                    )
                    .padding(dimensionResource(id = R.dimen.tinyPadding))
                    .background(color = Color.White)
                    .size(dimensionResource(id = R.dimen.logoSize))
                    .align(alignment = Alignment.CenterHorizontally)
            )
            Spacer(Modifier.size(dimensionResource(id = R.dimen.largePadding)))

            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append("S")
                }
                withStyle(style = SpanStyle(color = Color.Black)) {
                    append("ign")
                }

                withStyle(style = SpanStyle(color = Color.Red)) {
                    append(" U")
                }
                withStyle(style = SpanStyle(color = Color.Black)) {
                    append("p")
                }
            }, fontSize = MaterialTheme.typography.h4.fontSize)

            Spacer(Modifier.size(dimensionResource(id = R.dimen.largePadding)))

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
                    .focusRequester(focusRequester)
                    .onFocusEvent { event ->
                        if (event.isFocused) {
                            coroutineScope.launch {
                                bringFocusRequest.bringIntoView()
                            }
                        }
                    },
                label = {
                    Text(text = "Enter Email*")
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequester.requestFocus() }

                ),
            )
            if (emailErrorState.value) {
                Text(text = "Required", color = Color.Red)
            }
            Spacer(Modifier.size(dimensionResource(id = R.dimen.largePadding)))

            OutlinedTextField(
                value = password.value,
                onValueChange = {
                    if (passwordErrorState.value) {
                        passwordErrorState.value = false
                    }
                    password.value = it
                },
                isError = passwordErrorState.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                label = {
                    Text(text = "Enter Password*")
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequester.requestFocus() }

                ),
            )
            if (passwordErrorState.value) {
                Text(text = "Required", color = Color.Red)
            }
            Spacer(Modifier.size(dimensionResource(id = R.dimen.largePadding)))
            OutlinedTextField(
                value = confirmPassword.value,
                onValueChange = {
                    if (confirmPasswordErrorState.value) {
                        confirmPasswordErrorState.value = false
                    }
                    confirmPassword.value = it
                },
                isError = confirmPasswordErrorState.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusEvent { event ->
                        if (event.isFocused) {
                            coroutineScope.launch {
                                bringFocusRequest.bringIntoView()
                            }
                        }
                    },
                label = {
                    Text(text = "Confirm Password*")
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequester.requestFocus() }

                ),
            )
            if (confirmPasswordErrorState.value) {
                Text(text = "Required", color = Color.Red)
            }
            if (passwordMisMatch.value) {
                Text(text = "Password MisMatch", color = Color.Red)
            }
            Spacer(Modifier.size(dimensionResource(id = R.dimen.largePadding)))

            OutlinedTextField(
                value = phoneNumber.value,
                onValueChange = {
                    if (phoneErrorState.value) {
                        phoneErrorState.value = false
                    }
                    phoneNumber.value = it
                },
                visualTransformation=PrefixTransformation("(${AppConstants.IND_COUNTRY_CODE})${AppConstants.WORD_SPACE}"),
                isError = phoneErrorState.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusEvent { event ->
                        if (event.isFocused) {
                            coroutineScope.launch {
                                bringFocusRequest.bringIntoView()
                            }
                        }
                    },
                label = {
                    Text(text = "Enter Phone*")
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }

                )
            )
            if (phoneErrorState.value) {
                Text(text = "Required", color = Color.Red)
            }
            Spacer(
                Modifier
                    .size(dimensionResource(id = R.dimen.largePadding))
                    .bringIntoViewRequester(bringFocusRequest),)
            Button(
                onClick = {
                    when {
                        email.value.text.isEmpty() -> {
                            emailErrorState.value = true
                        }
                        password.value.text.isEmpty() -> {
                            passwordErrorState.value = true
                        }
                        confirmPassword.value.text.isEmpty() -> {
                            confirmPasswordErrorState.value = true
                        }
                        phoneNumber.value.text.isEmpty() -> {
                            phoneErrorState.value = true
                        }
                        password.value.text != confirmPassword.value.text -> {
                            passwordMisMatch.value = true
                        }
                        else -> {
                            passwordErrorState.value = false
                            emailErrorState.value = false
                            confirmPasswordErrorState.value = false
                            phoneErrorState.value = false
                            passwordMisMatch.value = false

                            pref.setUserDetails(PrefConstants.USER_DETAILS,UserDetails("",email.value.text,password.value.text,"+91${phoneNumber.value.text}",""))
                            viewModel.sendVerificationCodes(
                                phoneNumber = "+91${phoneNumber.value.text}"
                           )


                        }

                    }
                },
                content = {
                    Text(text = "Register", color = Color.White)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Red)
            )
            Spacer(
                Modifier
                    .size(dimensionResource(id = R.dimen.largePadding))
                    .bringIntoViewRequester(bringFocusRequest),)




        }

//
//    when (viewModel.verificationStatus) {
//        1 -> {
//      viewModel.regUser(mDb=mDb,email=email.value.text,password=password.value.text,phoneNumber=phoneNumber.value.text,"")
//        }
//        2 -> {
//            Toast.makeText(context,"Authentication Failed",Toast.LENGTH_SHORT).show()
//        }
//        3 -> {
//            if(!otpStatus.value) {
//                otpStatus.value=true
//                navController.currentBackStackEntry?.savedStateHandle?.set("ScreenType",AppConstants.REGISTER_SCREEN)
//                navController.navigate(NavItem.OTPScreen.route)
//
//            }
//        }
//        else->{
//            //Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show()
//        }
//    }
}
