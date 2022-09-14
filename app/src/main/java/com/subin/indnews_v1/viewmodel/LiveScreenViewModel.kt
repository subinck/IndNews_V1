package com.subin.indnews_v1.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subin.indnews_v1.Utility.Utils
import com.subin.indnews_v1.model.LiveChannels
import com.subin.indnews_v1.model.Malayalam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LiveScreenViewModel @Inject constructor():ViewModel() {

}