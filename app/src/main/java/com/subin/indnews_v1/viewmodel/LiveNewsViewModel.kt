package com.subin.indnews_v1.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subin.indnews_v1.model.response.LiveNewsArticle
import com.subin.indnews_v1.model.response.TechNewsArticle
import com.subin.indnews_v1.usecase.LiveNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LiveNewsViewStates{
    object Loading: LiveNewsViewStates()
    data class Success(val data: List<LiveNewsArticle>):LiveNewsViewStates()
    data class  Error(val errorMessage:String?):LiveNewsViewStates()
}

@HiltViewModel
class LiveNewsViewModel @Inject constructor(
    private val useCase: LiveNewsUseCase
):ViewModel() {

    private val _liveNewsDetails: MutableState<LiveNewsViewStates> = mutableStateOf(LiveNewsViewStates.Loading)
    val liveNewsDetails : State<LiveNewsViewStates> = _liveNewsDetails

    fun getLiveNews(){
        viewModelScope.launch {
            try {
                val response=useCase()
                if (response.isSuccessful){
                    Log.d("KK","$response")
                    _liveNewsDetails.value=LiveNewsViewStates.Success(response.body()?.articles!!)
                }else{
                    Log.d("KK","Error")
                    _liveNewsDetails.value=LiveNewsViewStates.Error("Error")
                }

            }catch (e:Exception){
                _liveNewsDetails.value=LiveNewsViewStates.Error("Error${e.message}")
            }
        }

    }
}