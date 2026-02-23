package com.example.kegelcontrol.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kegelcontrol.ui.components.CustomButton
import com.example.kegelcontrol.ui.components.scaledSp
import com.example.kegelcontrol.viewmodel.CronoViewModel
import com.example.kegelcontrol.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    cronoViewModel: CronoViewModel = viewModel()
) {
    var selectedRoute by remember { mutableStateOf<String?>(null) }

    val module = selectedRoute?.let { ScreenModule.findByRoute(it) }

    if (module != null) {
        module.Content(
            onBack = { selectedRoute = null },
            cronoViewModel = cronoViewModel
        )
    } else {
        ModuleListScreen(
            onModuleSelected = { selectedRoute = it }
        )
    }
}

@Composable
private fun ModuleListScreen(
    onModuleSelected: (String) -> Unit
) {
    val titleSp = scaledSp(28)
    // Chequeo en cada visualización: se cargan solo los botones de los módulos actuales (más módulos = más botones, menos = menos).
    val modules = ScreenModule.all()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF1E1E1E), Color(0xFF121212))))
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Selecciona un módulo",
                color = Color.White,
                fontSize = titleSp
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    items = modules,
                    key = { it.route }
                ) { module ->
                    CustomButton(
                        text = module.name,
                        onClick = { onModuleSelected(module.route) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2f)
        )
    }
}
