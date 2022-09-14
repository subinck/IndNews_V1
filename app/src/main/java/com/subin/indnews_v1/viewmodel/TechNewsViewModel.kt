package com.subin.indnews_v1.viewmodel


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subin.indnews_v1.model.response.TechNewsArticle
import com.subin.indnews_v1.usecase.TechNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class TechNewsViewStates{
    object Loading: TechNewsViewStates()
    data class Success(val data: List<TechNewsArticle>):TechNewsViewStates()
    data class  Error(val errorMessage:String?):TechNewsViewStates()
}

@HiltViewModel
class TechNewsViewModel@Inject constructor(
    private  val useCase: TechNewsUseCase
):ViewModel(){

    private val _techNewsDetails: MutableState<TechNewsViewStates> = mutableStateOf(TechNewsViewStates.Loading)
    val techNewsDetails : State<TechNewsViewStates> = _techNewsDetails

    init {
        getTechNews()

    }

    fun getTechNews(){
        viewModelScope.launch {
            try {
                val response=useCase.getTechNews()
                if (response.isSuccessful){
                    Log.d("KK","$response")
                    _techNewsDetails.value=TechNewsViewStates.Success(response.body()?.articles!!)
                }else{
                    Log.d("KK","Error")
                    _techNewsDetails.value=TechNewsViewStates.Error("Error")
                }

            }catch (e:Exception){
                _techNewsDetails.value=TechNewsViewStates.Error("Error${e.message}")
            }
        }

    }

}