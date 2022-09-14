package com.subin.indnews_v1.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.subin.indnews_v1.Constants.PrefConstants
import com.subin.indnews_v1.SharedPreference.IndNewsSharedPref
import com.subin.indnews_v1.db.IndNewsDAO
import com.subin.indnews_v1.model.CategoryItems
import com.subin.indnews_v1.model.UserDetails
import com.subin.indnews_v1.model.response.TechNewsArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CategoryViewState{
    object Loading: CategoryViewState()
    data class Success(val data: Boolean):CategoryViewState()
    data class  Error(val errorMessage:String?):CategoryViewState()
}

@HiltViewModel
class CategoryScreenViewModel @Inject constructor(
    private val dao: IndNewsDAO,
    private val pref: IndNewsSharedPref

    ):ViewModel() {
    private val _categoryScreenStatus: MutableState<CategoryViewState> = mutableStateOf(CategoryViewState.Loading)
    val categoryScreenStatus : State<CategoryViewState> = _categoryScreenStatus
    val fDb: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var user: MutableMap<String, Any?>
    val currentUser: FirebaseUser?= FirebaseAuth.getInstance().currentUser
    var uuid:String?=null

    init {
        getUserDetails()
       pref.setIsLogin(PrefConstants.IS_LOGIN,true)
    }

    fun insertCategoryItems(items:List<CategoryItems>){
        viewModelScope.launch {
            try {
            val res= dao.insertCategoryDetails(items)
                dao.insertCategoryDetails(items)
                if (res.size>0L){
                   _categoryScreenStatus.value=CategoryViewState.Success(true)
                }else{
                    _categoryScreenStatus.value=CategoryViewState.Error("Error Occurred")
                }
            }catch (e:Exception){

            }
        }
    }
  private  fun getUserDetails() {
        var u: UserDetails? = null
        uuid=currentUser?.uid
        pref.setUserUID(PrefConstants.USER_UID,uuid!!)
        val docRef = fDb.collection("client").document(uuid!!)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    // Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    user = document.data!!
                    Log.d("JK", "user:$user")
                    u = UserDetails(
                        user["Name"].toString(),
                        user["Email"].toString(),
                        user["Password"].toString(),
                        user["Phone"].toString(),
                        user["State"].toString()
                    )
                    Log.d("JK", "user:$u")
                    pref.setUserDetails(PrefConstants.USER_DETAILS,u!!)
                } else {
                    // Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                // Log.d(TAG, "get failed with ", exception)
            }
    }
}