// ui/modules/ModuleRegistry.kt

package com.example.kegelcontrol.ui.modules

import com.example.kegelcontrol.ui.screens.CronoScreen
import com.example.kegelcontrol.ui.screens.ResistenceScreen
//import com.example.kegelcontrol.ui.screens.DetailScreen

data class AppModule(
    val name: String,
    val route: String,
)

val registeredModules = listOf(
    AppModule("Resistencia", "resistencia"),
    AppModule("Cronómetro", "crono")
    //AppModule("Detalle", "detalle")
)
