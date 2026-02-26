package com.example.kegelcontrol.ui.util

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * Devuelve un tamaño de fuente más pequeño si el dispositivo está en modo horizontal.
 * Reduce el tamaño de la fuente en un 25% en modo horizontal.
 */
@Composable
fun getAdaptiveFontSize(portraitSize: TextUnit): TextUnit {
    val configuration = LocalConfiguration.current
    return when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            (portraitSize.value * 0.75).sp
        }
        else -> {
            portraitSize
        }
    }
}
