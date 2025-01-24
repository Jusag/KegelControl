package com.example.kegelcontrol.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.kegelcontrol.R

val customFont = FontFamily(
    Font(R.font.ltstopwatch_regular)
)


@Composable
fun CronoText(time: String) {
    Text(
        text = time,
        style = TextStyle(
            fontFamily = customFont,
            fontSize = 40.sp,
            color = Color.Black
            // Cambiar Fuente
        )
    )

}