package com.subin.indnews_v1.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asImageBitmap
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.subin.indnews_v1.Constants.PrefConstants
import com.subin.indnews_v1.R
import com.subin.indnews_v1.SharedPreference.IndNewsSharedPref
import com.subin.indnews_v1.Utility.Utils
import com.subin.indnews_v1.db.IndNewsDAO
import com.subin.indnews_v1.fragment.HomeFragment
import com.subin.indnews_v1.fragment.LoginFragment
import com.subin.indnews_v1.model.UserDetails
import com.subin.indnews_v1.model.entity.ProfileImageEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val preference:IndNewsSharedPref,
    private val dao:IndNewsDAO
):ViewModel() {
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var user: MutableMap<String, Any?>
    private val uuid = preference.getUserUID(PrefConstants.USER_UID)
    private val userDetails = preference.getUserDetails(PrefConstants.USER_DETAILS)
    private val password = userDetails.password
    private val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference
    val bitmapFile =  mutableStateOf<Bitmap?>(null)
    val bitmapLocalDB =  mutableStateOf<Bitmap?>(null)

    init {
        getUserDetails()
        getProfileImages()

//        viewModelScope.launch {
//            val profile=dao.getProfileImage(uuid)
//            bitmapLocalDB.value = profile.data?.let { Utils.byteArrayToBitmap(it) }
//        }
    }

    fun getUserDetails() {
        var u: UserDetails? = null
        val docRef = firebaseFirestore.collection("client").document(uuid)
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
                } else {
                    // Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                // Log.d(TAG, "get failed with ", exception)
            }
    }

    fun saveOrUpdateProfileDetails(name: String, state: String, email: String, phone: String) {
        // To update specific Item
        firebaseFirestore.collection("client").document(uuid).update(
            mapOf(
                "Name" to name,
                "Email" to email,
                "State" to state,
                "Phone" to phone
            )
        )
            .addOnSuccessListener {
                Log.d("TAG", "DocumentSnapshot successfully updted")
                preference.setUserDetails(
                    PrefConstants.USER_DETAILS,

                    UserDetails(name = name, email = email, password = password, phone = phone, state = state)
                )
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error updating document", e)
            }
    }

    fun uploadImage(filePath: Uri) {

        val ref: StorageReference = storageReference.child("images/$uuid.jpeg")
        ref.putFile(filePath).addOnSuccessListener(
            OnSuccessListener<UploadTask.TaskSnapshot>() { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                    val imageUrl = it.toString()
                }

            })
            .addOnFailureListener(OnFailureListener { e ->
                println(e.message)
            })


    }

  private  fun getProfileImages() {

          var bitmap: Bitmap? = null
          val stRef = storageReference.child("images/$uuid.jpeg")
          val localFile = File.createTempFile("images", "jpeg")
          stRef.getFile(localFile).addOnSuccessListener {
              viewModelScope.launch() {
                  bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                  bitmapFile.value = bitmap
                  if (bitmap != null) {
                      val byteArray = Utils.bitmapToByteArray(bitmap!!)
                      dao.insertProfileImage(ProfileImageEntity(uuid, byteArray))
                  }
              }
          }
              .addOnFailureListener { error ->
                  Log.d("TAG", "Error:$error")
              }


      }

    fun doLogout(activity: FragmentActivity){
        Firebase.auth.signOut()
        val fragmentManager = activity.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, LoginFragment())
        transaction.addToBackStack("LogoutScreen")
        transaction.commit()

    }


    }

