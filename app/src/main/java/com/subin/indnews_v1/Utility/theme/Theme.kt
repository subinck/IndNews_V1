package com.subin.indnews_v1.Utility.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.subin.indnews_v1.Utility.*

private val LightThemeColors= lightColors(
primary = Red700,
primaryVariant = Red600,
secondary = White2,
secondaryVariant= Teal300,
background= Grey1,
surface= White2,
error= RedErrorDark,
onPrimary= Black2,
onSecondary= Black1 ,
onBackground= Black1,
onSurface= Black1,
onError= RedErrorLight

)

private val DarkThemeColors= darkColors(
    primary = Red800,
    primaryVariant = White2,
    secondary = Black1,
    secondaryVariant= Teal300,
    background= Black1,
    surface= Black1,
    error= RedErrorLight,
    onPrimary= White2,
    onSecondary= White2 ,
    onBackground= White2,
    onSurface= White2,
    onError= RedErrorDark
)

@Composable
fun AppTheme(
    darkTheme:Boolean,
    content:@Composable ()-> Unit
){
  MaterialTheme(
      colors = if (darkTheme) DarkThemeColors else LightThemeColors
  ){
       content()
  }
}