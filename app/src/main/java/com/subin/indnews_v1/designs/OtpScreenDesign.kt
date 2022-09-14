package com.subin.indnews_v1.designs

import android.app.Activity
import android.os.CountDownTimer
import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
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
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.google.firebase.firestore.FirebaseFirestore
import com.subin.indnews_v1.Constants.AppConstants
import com.subin.indnews_v1.NavItem
import com.subin.indnews_v1.R
import com.subin.indnews_v1.Utility.PrefixTransformation
import com.subin.indnews_v1.Utility.Toast.CustomToastUtil
import com.subin.indnews_v1.fragment.HomeFragment
import com.subin.indnews_v1.viewmodel.LoginViewStates
import com.subin.indnews_v1.viewmodel.RegisterViewStates
import com.subin.indnews_v1.viewmodel.RegistrationViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.time.seconds

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun OtpScreenDesign(
    navController: NavController,
    activity:Activity,
    screenType:String,
    viewModel: RegistrationViewModel= hiltViewModel()) {
    val context = LocalContext.current
    var otpVal: String? = null
    val phoneNumber= remember { mutableStateOf("")}
    val customView= remember {LottieAnimationView(context)}
    val phoneErrorStateOtp = remember { mutableStateOf(false) }
    val (focusRequester) = FocusRequester.createRefs()
    val mDb=FirebaseFirestore.getInstance()
    val bringFocusRequest = BringIntoViewRequester()
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        println("found activity?$activity")
        val activity1 = activity ?: return@LaunchedEffect
        viewModel.setActivity(activity1)

    }
    val viewState by remember { viewModel.registerDetails }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(colorResource(id = R.color.color_app_theme)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            when(val state=viewState){
                is RegisterViewStates.Success ->{
                    moveToHomeScreenFromOtp(activity as FragmentActivity)

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

            Text(
                text = "Phone Number Verification",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
          /*  Image(
                painter = painterResource(id = R.drawable.otp),
                contentDescription = "Otp Image",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
            )*/

            AndroidView({
                customView
            },
                modifier= Modifier
                    .width(200.dp)
                    .height(200.dp)
                    .onFocusEvent { event ->
                        if (event.isFocused) {
                            coroutineScope.launch {
                                bringFocusRequest.bringIntoView()
                            }
                        }
                    },
            ){view->
                with(view){
                    setAnimation(R.raw.phone_number_verify)
                    playAnimation()
                    repeatCount=LottieDrawable.INFINITE
                    foregroundGravity=Gravity.CENTER
                }

            }

            Spacer(modifier = Modifier.height(75.dp))

            Text(
                text = "Enter the OTP",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            OTPTextFields(
                modifier = Modifier.onFocusEvent { event ->
                    if (event.isFocused) {
                        coroutineScope.launch {
                            bringFocusRequest.bringIntoView()
                        }
                    }
                },
                length = 6
            ) {
                    getOpt ->
                otpVal=getOpt
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    if (otpVal != null) {
                    viewModel.otpVerification(otpVal!!,activity,phoneNumber.value,screenType)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(45.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(colorResource(id = R.color.color_app_theme))
                    .bringIntoViewRequester(bringFocusRequest)
            ) {
                Text(
                    text = "Verify",
                    fontSize = 15.sp,
                    color = Color.White
                )

            }
//            Text(text = stringResource(R.string.otp_resend),
//                modifier = Modifier.padding(8.dp)
//                    .align(Alignment.CenterHorizontally),
//                color = Color.Red,
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Bold)
        }
    }

//    when (viewModel.regState.value) {
//        1 -> {
//            CustomToastUtil.ToastError(
//                message = "User already exist !",
//                duration = Toast.LENGTH_LONG,
//                padding = PaddingValues(top = dimensionResource(id = R.dimen.largePadding)),
//                contentAlignment = Alignment.TopCenter
//            )
//
//        }
//        2 -> {
//            //ToastSuccess(message = "Success !", duration = Toast.LENGTH_LONG, padding = PaddingValues(top = dimensionResource(id = R.dimen.largePadding)), contentAlignment = Alignment.TopCenter)
//           // navController.navigate(NavItem.HomeScreen.route)
//            moveToHomeScreenFromOtp(activity as FragmentActivity)
//        }
//        3 -> {
//            CustomToastUtil.ToastError(
//                message = "Failed !",
//                duration = Toast.LENGTH_LONG,
//                padding = PaddingValues(top = 16.dp),
//                contentAlignment = Alignment.TopCenter
//            )
//
//        }
//    }
}

fun moveToHomeScreenFromOtp(activity:FragmentActivity){
    val fragmentManager = activity.supportFragmentManager
    val transaction = fragmentManager.beginTransaction()
    transaction.replace(R.id.main_container, HomeFragment())
    transaction.addToBackStack("HomeScreen")
    transaction.commit()
}

@Preview(showBackground = true)
@Composable
fun OtpScreenPreview(){
    val context= LocalContext.current
    val activity=Activity()
    val navController=NavController(context)
    val mDb:FirebaseFirestore=FirebaseFirestore.getInstance()
  OtpScreenDesign(navController,activity,"")
}