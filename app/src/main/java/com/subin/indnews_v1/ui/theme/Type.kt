package com.subin.indnews_v1.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.subin.indnews_v1.R

// Set of Material typography styles to start with

//
//val Andada= FontFamily(
//    Font(R.font.andada)
//)
//val Algeria_Sans= FontFamily(
//    Font(R.font.alegreya_sans_sc_thin),
//    Font(R.font.alegreya_sans_medium),
//    Font(R.font.alegreya_sans_bold)
//)

val Typography = Typography(
//     h1=TextStyle(
//        fontFamily = Andada,
//        fontWeight = FontWeight.Bold,
//        fontSize = 18.sp
//    ),
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )

    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)