package com.subin.indnews_v1.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.subin.indnews_v1.Constants.AppConstants
import com.subin.indnews_v1.Constants.PrefConstants
import com.subin.indnews_v1.SharedPreference.IndNewsSharedPref
import com.subin.indnews_v1.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

sealed class RegisterViewStates{
    object Loading: RegisterViewStates()
    data class  ProgressState(val progress:Boolean?):RegisterViewStates()
    data class  Success(val data: String?):RegisterViewStates()
    data class  Error(val errorMessage:String?):RegisterViewStates()
}
@HiltViewModel
class RegistrationViewModel @Inject constructor(
 private val  networkHelper: NetworkHelper,
 private val preference:IndNewsSharedPref
):ViewModel() {

    var regState = mutableStateOf(value = 0)
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var verificationStatus by mutableStateOf(0)
    var verificationOtp = ""
    private lateinit var baseBuilder: PhoneAuthOptions.Builder
    private lateinit var mDb:FirebaseFirestore
    private lateinit var email: String
    private lateinit var password:String
    private val _registerDetails: MutableState<RegisterViewStates> = mutableStateOf(RegisterViewStates.ProgressState(false))
    val registerDetails : State<RegisterViewStates> = _registerDetails


    init {
        val currentUser = auth.currentUser
    // updateUI(currentUser)
    }
    fun setActivity(activity: Activity) {
        baseBuilder = PhoneAuthOptions.newBuilder().setActivity(activity)

    }
    fun sendVerificationCodes(
        phoneNumber: String,
    ) {
        _registerDetails.value=RegisterViewStates.ProgressState(true)
        val options= baseBuilder
        .setPhoneNumber(phoneNumber)
        .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks(object :
                PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    // handledException(customMessage = "Verification Completed")
                   // verificationStatus.value=1
                    _registerDetails.value=RegisterViewStates.ProgressState(false)

                }
                override fun onVerificationFailed(p0: FirebaseException) {
                    // handledException(customMessage = "Verification Failed")
                   // verificationStatus.value=2
                    _registerDetails.value=RegisterViewStates.Success("Authentication Failed")
                    _registerDetails.value=RegisterViewStates.ProgressState(false)

                }

                override fun onCodeSent(otp: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(otp, p1)
                    preference.setAuthenticationVariable(PrefConstants.AUTHENTICATION_VARIABLE,otp)
                    verificationOtp = otp
                    // handledException(customMessage = "Otp Send Successfully")
                    preference.setCodeToken(PrefConstants.TOKEN_VARIABLE,p1.toString())
                    verificationStatus=3
                    _registerDetails.value=RegisterViewStates.ProgressState(false)
                    _registerDetails.value=RegisterViewStates.Success("Code send")

                }
            }).build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun otpVerification(otp: String,activity: Activity,phoneNumber: String,screenType:String) {
        _registerDetails.value=RegisterViewStates.ProgressState(true)
        val verificationId=preference.getAuthenticationVariable(PrefConstants.AUTHENTICATION_VARIABLE)
        val credential = PhoneAuthProvider.getCredential(verificationId, otp)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                Log.d("Msg","Verification Successful")
                if (task.isSuccessful) {
                    if (screenType==AppConstants.LOGIN_SCREEN){
                        regState.value=2
                        _registerDetails.value=RegisterViewStates.ProgressState(false)
                        _registerDetails.value=RegisterViewStates.Success("Otp Verified")

                    }
                 else {
                        val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
                        val uuid = currentUser?.uid
                        preference.setUserUID(PrefConstants.USER_UID, uuid!!)
                        val userDetails = preference.getUserDetails(PrefConstants.USER_DETAILS)
                        _registerDetails.value=RegisterViewStates.ProgressState(false)
                        regUser(
                            firebaseFirestore,
                            userDetails.email,
                            userDetails.password,
                            userDetails.phone,
                            uuid
                        )
                    }
                } else {
                    // handledException(customMessage =  "Wrong Otp")
                    _registerDetails.value=RegisterViewStates.Error("Wrong Otp")
                    Log.d("Msg","Wrong Otp")
                }
            }
    }

     fun regUser(
         mDb: FirebaseFirestore,
         email: String,
         password: String,
         phoneNumber: String,
         uuid: String?
    ) {
         _registerDetails.value = RegisterViewStates.ProgressState(true)
         viewModelScope.launch {
             delay(2000)
             val ref = mDb.collection("client").document()
             ref.get()
                 .addOnSuccessListener(OnSuccessListener<DocumentSnapshot> { documentSnapshot ->
                     if (documentSnapshot.exists()) {
                         regState.value = 1
                         _registerDetails.value=RegisterViewStates.Success("User Exist ")
                         //  Toast.makeText(this@Register, "Sorry,this user exists", Toast.LENGTH_SHORT).show()
                         Log.d("JJ", "Sorry,this user exists")
                     } else {
                         val reg_entry: MutableMap<String, Any> = HashMap()
                         reg_entry["Name"] = ""
                         reg_entry["Email"] = email
                         reg_entry["Password"] = password
                         reg_entry["Phone"] = phoneNumber
                         reg_entry["State"] = ""

                         //   String myId = ref.getId();
                         firebaseFirestore.collection("client").document(uuid.toString())
                             .set(reg_entry)
                             .addOnSuccessListener {
                                 regState.value = 2
                                 Log.d("JJ", "Success")

                                 _registerDetails.value=RegisterViewStates.ProgressState(false)
                                 _registerDetails.value=RegisterViewStates.Success("Registration Success ")
                             }
                             .addOnFailureListener { e ->
                                 Log.d("Error", e.message!!)

                                 _registerDetails.value=RegisterViewStates.ProgressState(false)
                                 regState.value = 3
                                 _registerDetails.value=RegisterViewStates.Success("Registration Failed ")
                             }
                     }
                 })
         }
     }






    }

