package com.subin.indnews_v1.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subin.indnews_v1.Constants.PrefConstants
import com.subin.indnews_v1.SharedPreference.IndNewsSharedPref
import com.subin.indnews_v1.Utility.Utils
import com.subin.indnews_v1.db.IndNewsDAO
import com.subin.indnews_v1.model.entity.ProfileImageEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    val pref: IndNewsSharedPref,
    val dao: IndNewsDAO
):ViewModel() {
      val bitmapFile =  mutableStateOf<Bitmap?>(null)
    private  var profileImage:ProfileImageEntity?=null


     fun getProfileImage(){
         val uuid=pref.getUserUID(PrefConstants.USER_UID)
         viewModelScope.launch {
             profileImage = dao.getProfileImage(uuid)
             if (profileImage!=null) {
                 val byteArray = profileImage?.data
                 bitmapFile.value = Utils.byteArrayToBitmap(byteArray!!)
                 Log.d("LL", "LL${bitmapFile.value}")
             }else{

             }
         }
     }
}