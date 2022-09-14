package com.subin.indnews_v1.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subin.indnews_v1.model.response.Article
import com.subin.indnews_v1.usecase.ArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class ArticleViewStates{
    object Loading: ArticleViewStates()
    data class Success(val data: List<Article>):ArticleViewStates()
    data class  Error(val errorMessage:String?):ArticleViewStates()
}

@HiltViewModel
class ArticleScreenViewModel @Inject constructor(
 val useCase: ArticleUseCase,
): ViewModel() {
    private val _articleDetails: MutableState<ArticleViewStates> = mutableStateOf(ArticleViewStates.Loading)
    val articleDetails : State<ArticleViewStates> = _articleDetails


    fun getArticles(type: String, dateRangeFrom: String, dateRangeTo: String) {
        viewModelScope.launch {
            try {
                val response = useCase(type, dateRangeFrom, dateRangeTo)
                if (response.isSuccessful) {
                    if (response.body()?.articles?.size!! >0) {
                        Log.d("ARTICLE", "Success=$response")
                        _articleDetails.value = ArticleViewStates.Success(response.body()?.articles!!)
                    }else{
                      _articleDetails.value=  ArticleViewStates.Error("No Data Found")
                    }

                } else {
                    _articleDetails.value=  ArticleViewStates.Error("No Data Found")
                    Log.d("ARTICLE", "Failed=$response")
                }
            } catch (e: Exception) {
                _articleDetails.value=  ArticleViewStates.Error("{${e.message}")
                Log.d("ARTICLE", "Failed=$e")
            }
        }

    }

}