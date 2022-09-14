package com.subin.indnews_v1.viewmodel

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.subin.indnews_v1.Constants.PrefConstants
import com.subin.indnews_v1.SharedPreference.AppPreference
import com.subin.indnews_v1.SharedPreference.IndNewsSharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject



sealed class LoginViewStates{
    object Loading: LoginViewStates()
    data class  ProgressState(val progress:Boolean?):LoginViewStates()
    data class  Success(val data: String?):LoginViewStates()
    data class  Error(val errorMessage:String?):LoginViewStates()
}
@HiltViewModel
class LoginScreenViewModel @Inject constructor(
   private val appPreference: AppPreference,
     val preference: IndNewsSharedPref
):
    ViewModel() {
    var loginState= mutableStateOf(value=0)
    var currentUserState= mutableStateOf(value=false)
    var verificationState= mutableStateOf(value=0)
    val fDb: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var user: MutableMap<String, Any?>
    val currentUser: FirebaseUser?= FirebaseAuth.getInstance().currentUser
    var uuid:String?=null
    private lateinit var baseBuilder: PhoneAuthOptions.Builder

    private val _loginDetails: MutableState<LoginViewStates> = mutableStateOf(LoginViewStates.ProgressState(false))
    val loginDetails : State<LoginViewStates> = _loginDetails

    fun setActivity(activity: Activity) {
        baseBuilder = PhoneAuthOptions.newBuilder().setActivity(activity)

    }
    fun loginSendVerificationCodes(
        phoneNumber: String,
    ) {
        _loginDetails.value=LoginViewStates.ProgressState(true)
        val options= baseBuilder
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks(object :
                PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    // handledException(customMessage = "Verification Completed")
                    verificationState.value=1
                    _loginDetails.value = LoginViewStates.ProgressState(false)

                }
                override fun onVerificationFailed(p0: FirebaseException) {
                    // handledException(customMessage = "Verification Failed")
                    verificationState.value=2
                    _loginDetails.value = LoginViewStates.ProgressState(false)

                }

                override fun onCodeSent(otp: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(otp, p1)
                    preference.setAuthenticationVariable(PrefConstants.AUTHENTICATION_VARIABLE,otp)
                 //   verificationOtp = otp
                    // handledException(customMessage = "Otp Send Successfully")
                    verificationState.value=3
                    _loginDetails.value = LoginViewStates.ProgressState(false)

                }
            }).build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }



    fun doLogin(mDb:FirebaseFirestore,email:String,password:String){

            _loginDetails.value = LoginViewStates.ProgressState(true)
        viewModelScope.launch {
            delay(2000)
            mDb.collection("client")
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    if (task.isSuccessful) {
                        for (doc in task.result) {
                            val a = doc.getString("Email")
                            val b = doc.getString("Password")
                            if (a.equals(email, ignoreCase = true) and b.equals(
                                    password,
                                    ignoreCase = true
                                )
                            ) {
                                loginState.value = 1
                                // appPreference.isLogin=true
                                Log.d("JJ", "Success")
                                _loginDetails.value = LoginViewStates.ProgressState(false)
                                _loginDetails.value = LoginViewStates.Success("Login Successful")

                                break
                            } else {
                                loginState.value = 2
                                Log.d("JJ", "Failed")
                                _loginDetails.value = LoginViewStates.ProgressState(false)
                                _loginDetails.value = LoginViewStates.Success("Login Failed")

                            }
                        }
                    }
                })
        }
    }


}