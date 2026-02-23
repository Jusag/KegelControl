package com.example.kegelcontrol.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kegelcontrol.ui.components.CustomButton
import com.example.kegelcontrol.ui.components.scaledSp

@Composable
fun DetailScreen(
    onBack: () -> Unit = {}
) {
    val titleSp = scaledSp(28)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF1E1E1E), Color(0xFF121212))))
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomButton(
                text = "← Inicio",
                onClick = onBack,
                modifier = Modifier.padding(end = 8.dp)
            )
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Detalle",
                    color = Color.White,
                    fontSize = titleSp
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Contenido del módulo Detalle",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = titleSp
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2f)
        )
    }
}
