package com.example.kegelcontrol.ui.screens

import androidx.compose.runtime.Composable
import com.example.kegelcontrol.viewmodel.CronoViewModel

/**
 * Registro de todos los módulos de la app.
 * Cada pantalla en el directorio "screens" (excepto HomeScreen) debe tener una entrada aquí.
 */
sealed class ScreenModule(
    val name: String,
    val route: String
) {
    @Composable
    abstract fun Content(onBack: () -> Unit, cronoViewModel: CronoViewModel)

    data object Crono : ScreenModule("Cronómetro", "crono") {
        @Composable
        override fun Content(onBack: () -> Unit, cronoViewModel: CronoViewModel) {
            CronoScreen(onBack = onBack, viewModel = cronoViewModel)
        }
    }

    data object Resistencia : ScreenModule("Resistencia", "resistencia") {
        @Composable
        override fun Content(onBack: () -> Unit, cronoViewModel: CronoViewModel) {
            ResistenceScreen(onBack = onBack, viewModel = cronoViewModel)
        }
    }

    data object Detail : ScreenModule("Detalle", "detalle") {
        @Composable
        override fun Content(onBack: () -> Unit, cronoViewModel: CronoViewModel) {
            DetailScreen(onBack = onBack)
        }
    }

    companion object {
        /** Lista de todos los módulos (todas las pantallas en screens salvo HomeScreen). */
        fun all(): List<ScreenModule> = listOf(
            Crono,
            Resistencia,
            Detail
        )

        fun findByRoute(route: String): ScreenModule? = all().find { it.route == route }
    }
}
