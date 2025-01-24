package com.example.kegelcontrol.ui.components

import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun CustomButton(
    text: String,
    onClick:()->Unit
) {
   Button(
       onClick = onClick
   ){
       Text(text = text)
   }
}


