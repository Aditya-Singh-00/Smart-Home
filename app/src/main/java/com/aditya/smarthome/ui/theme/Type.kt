package com.aditya.smarthome.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.aditya.smarthome.R

val quicksand = FontFamily(
    Font(R.font.quicksand_light, FontWeight.Light),
    Font(R.font.quicksand_regular, FontWeight.Normal),
    Font(R.font.quicksand_medium, FontWeight.Medium),
    Font(R.font.quicksand_semi_bold, FontWeight.SemiBold),
    Font(R.font.quicksand_bold, FontWeight.Bold)
)


val Typography = Typography(
    h1 = TextStyle(
        fontFamily = quicksand,
        fontSize = 24.sp
    ),
    h2 = TextStyle(fontFamily = quicksand),
    h3 = TextStyle(fontFamily = quicksand),
    h4 = TextStyle(fontFamily = quicksand),
    h5 = TextStyle(fontFamily = quicksand),
    body1 = TextStyle(
        fontFamily = quicksand,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = quicksand,
        fontSize = 14.sp
    ),
    button = TextStyle(fontFamily = quicksand)

)