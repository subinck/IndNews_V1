package com.subin.indnews_v1.Toast

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainSnackBar(message:String){
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(message)
                }

    }
}


