package com.example.kegelcontrol.module

/**
 * Modelo de datos de un módulo (nombre + ruta).
 * La lista real de módulos se define en [com.example.kegelcontrol.ui.screens.ScreenModule]:
 * todos los archivos en el directorio "screens" son módulos excepto HomeScreen.
 */
data class AppModule(
    val name: String,
    val route: String,
)
