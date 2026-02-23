package com.example.kegelcontrol.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.TextUnit

/**
 * Referencia de altura para un teléfono medio (aprox. 640 dp de alto en portrait).
 * Se usa para escalar fuentes y espaciados en distintas resoluciones.
 */
private const val REFERENCE_HEIGHT_DP = 640f

/**
 * Factor de escala mínimo y máximo para no exagerar en pantallas muy pequeñas o muy grandes.
 */
private const val MIN_SCALE = 0.75f
private const val MAX_SCALE = 1.15f

/**
 * Devuelve un factor de escala según la altura de pantalla (solo portrait).
 * Pantallas más pequeñas que la referencia → escala < 1; más grandes → escala > 1.
 */
@Composable
fun rememberScaleFactor(): Float {
    val config = LocalConfiguration.current
    val heightDp = config.screenHeightDp.toFloat()
    return (heightDp / REFERENCE_HEIGHT_DP).coerceIn(MIN_SCALE, MAX_SCALE)
}

/**
 * Tamaño de fuente escalado según la resolución de pantalla (siempre vertical).
 */
@Composable
fun scaledSp(baseSp: Int): TextUnit {
    val scale = rememberScaleFactor()
    return (baseSp * scale).sp
}

/**
 * Espaciado en dp escalado según la resolución (opcional, para consistencia).
 */
@Composable
fun scaledDp(baseDp: Int): Dp {
    val scale = rememberScaleFactor()
    return (baseDp * scale).dp
}
