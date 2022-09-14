package com.subin.indnews_v1.designs


import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.withStyle
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.firebase.firestore.FirebaseFirestore
import com.subin.indnews_v1.Constants.AppConstants
import com.subin.indnews_v1.R
import com.subin.indnews_v1.NavItem
import com.subin.indnews_v1.Utility.PrefixTransformation
import com.subin.indnews_v1.Utility.Toast.CustomToastUtil
import com.subin.indnews_v1.fragment.HomeFragment
import com.subin.indnews_v1.viewmodel.LoginScreenViewModel
import com.subin.indnews_v1.viewmodel.LoginViewStates
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun LoginScreenDesign(navController: NavController,
                      activity1: FragmentActivity,
                      viewModel: LoginScreenViewModel= hiltViewModel(),
) {
    val context = LocalContext.current
    val email = remember { mutableStateOf(TextFieldValue()) }
    val emailErrorState = remember { mutableStateOf(false) }
    val passwordErrorState = remember { mutableStateOf(false) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val loginPhoneNumber = remember { mutableStateOf(TextFieldValue()) }
    val loginPhoneErrorState = remember { mutableStateOf(false) }
    val (focusRequester) = FocusRequester.createRefs()
    val scaffoldState = rememberScaffoldState()
    val mDb: FirebaseFirestore = FirebaseFirestore.getInstance()
    val otpNavigationState = remember { mutableStateOf(false) }
    val otpStatus= remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val bringFocusRequest = BringIntoViewRequester()

    val windowInfo = rememberWindowInfo()
    if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
        LaunchedEffect(Unit) {
            println("found activity?$activity1")
            val activity1 = activity1 ?: return@LaunchedEffect
            viewModel.setActivity(activity1)

        }
        val viewState by remember { viewModel.loginDetails }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.largePadding))
                .statusBarsPadding()
                .navigationBarsWithImePadding()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
        ) {

            when(val state=viewState){
                is LoginViewStates.Success ->{
                    viewModel.loginSendVerificationCodes(phoneNumber = "+91${loginPhoneNumber.value.text}")

                }
                is LoginViewStates.Error ->{
                    CustomToastUtil.ToastError(
                        message = "Login Failed !",
                        duration = Toast.LENGTH_LONG,
                        padding = PaddingValues(top = dimensionResource(id = R.dimen.largePadding)),
                        contentAlignment = Alignment.TopCenter)
                }
                is LoginViewStates.ProgressState-> {
                    if (state.progress == true) {
                            ProgressBarDesign()
                        }

                }
                is LoginViewStates.Loading->{

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
                    append(" I")
                }
                withStyle(style = SpanStyle(color = Color.Black)) {
                    append("n")
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

            val passwordVisibility = remember { mutableStateOf(true) }

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
                    .focusRequester(focusRequester)
                   .onFocusEvent { event ->
                   if (event.isFocused) {
                    coroutineScope.launch {
                        bringFocusRequest.bringIntoView()
                    }
                }
            },
                label = {
                    Text(text = "Enter Password*")
                },
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                    }) {
                        Icon(
                            imageVector = if (passwordVisibility.value)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff,

                            contentDescription = "visibility",
                            tint = Color.Red
                        )
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequester.requestFocus() }

                ),

                visualTransformation = if (passwordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None
            )
            if (passwordErrorState.value) {
                Text(text = "Required", color = Color.Red)
            }
            Spacer(Modifier.size(dimensionResource(id = R.dimen.largePadding)))

            OutlinedTextField(
                value = loginPhoneNumber.value,
                onValueChange = {
                    if (loginPhoneErrorState.value) {
                        loginPhoneErrorState.value = false
                    }
                    loginPhoneNumber.value = it
                },
                visualTransformation = PrefixTransformation("(${AppConstants.IND_COUNTRY_CODE})${AppConstants.WORD_SPACE}"),
                isError = loginPhoneErrorState.value,
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
                    onNext = { focusManager.clearFocus() }

                )
            )
            if (loginPhoneErrorState.value) {
                Text(text = "Required", color = Color.Red)
            }
            Spacer(Modifier.size(dimensionResource(id = R.dimen.largePadding)))
            Button(
                onClick = {
                    when {
                        email.value.text.isEmpty() -> {
                            emailErrorState.value = true
                        }
                        password.value.text.isEmpty() -> {
                            passwordErrorState.value = true
                        }
                        else -> {
                            passwordErrorState.value = false
                            emailErrorState.value = false
                            viewModel.doLogin(mDb, email.value.text, password.value.text)
                        }
                    }

                },
                content = {
                    Text(text = "Login", color = Color.White)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .bringIntoViewRequester(bringFocusRequest),
                colors = ButtonDefaults.buttonColors(Color.Red)
            )
            Spacer(Modifier.size(dimensionResource(id = R.dimen.largePadding)))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                TextButton(onClick = {
                    navController.navigate(NavItem.RegisterScreen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }) {
                    Text(text = "Register ?", color = Color.Red)
                }
            }
        }
    }





    when (viewModel.verificationState.value) {
        1 -> {

        }
        2 -> {
            Toast.makeText(context, "Authentication Failed", Toast.LENGTH_SHORT).show()
        }
        3 -> {
            if (!otpStatus.value) {
                otpStatus.value = true
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    "ScreenType",
                    AppConstants.LOGIN_SCREEN
                )
                navController.navigate(NavItem.OTPScreen.route)

            }
        }
        else -> {
            //Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show()
        }
    }
}
fun moveToHomeScreen(activity: FragmentActivity) {
    val fragmentManager = activity.supportFragmentManager
    val transaction = fragmentManager.beginTransaction()
    transaction.replace(R.id.main_container, HomeFragment())
    transaction.addToBackStack("HomeScreen")
    transaction.commit()
}
