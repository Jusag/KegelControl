package com.example.kegelcontrol.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kegelcontrol.R
import com.example.kegelcontrol.ui.components.CustomButton
import com.example.kegelcontrol.ui.components.scaledSp
import com.example.kegelcontrol.viewmodel.CronoViewModel

@Composable
fun CronoScreen(
    onBack: () -> Unit = {},
    viewModel: CronoViewModel = viewModel()
) {
    val time by viewModel.time.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()
    val lapsList by viewModel.lapsList.collectAsState()
    val avgLaps by viewModel.avgLaps.collectAsState()

    val customFont = FontFamily(Font(R.font.ltstopwatch_regular))
    val titleSp = scaledSp(55)
    val sectionTitleSp = scaledSp(35)
    val lapSp = scaledSp(25)
    val avgSp = scaledSp(30)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF1E1E1E), Color(0xFF121212))))
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Fila superior: botón Inicio + cronómetro (20%)
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
                    text = viewModel.formatTime(time.toLong()),
                    fontSize = titleSp,
                    fontFamily = customFont,
                    color = Color.White
                )
            }
        }

        // Zona central: lista acotada + promedio siempre visible (60%)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f)
                .background(Color(0xFF2A2A2A), shape = RoundedCornerShape(20.dp))
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Vueltas recientes",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = sectionTitleSp
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Lista de últimas 8 vueltas; ocupa el espacio restante y hace scroll
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    itemsIndexed(lapsList.takeLast(8).reversed()) { index, lap ->
                        Text(
                            "Vuelta ${lapsList.size - index} - Tiempo: ${viewModel.formatTime(lap.toLong())}",
                            color = Color.White,
                            fontSize = lapSp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (lapsList.isNotEmpty()) {
                    Text(
                        "Promedio: ${viewModel.formatTime(avgLaps)}",
                        color = Color.White,
                        fontSize = avgSp
                    )
                }
            }
        }

        // Botones abajo (20%)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2f)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomButton(
                text = if (isRunning) "Pause" else "Start",
                onClick = {
                    if (!isRunning) {
                        viewModel.startCrono()
                    } else {
                        viewModel.pauseCrono()
                    }
                }
            )

            CustomButton(
                text = "Lap",
                onClick = {
                    viewModel.addLap()
                }
            )

            CustomButton(
                text = "Reset",
                onClick = { viewModel.resetCrono() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCronoScreen() {
    val fakeViewModel = remember { CronoViewModel() }
    CronoScreen(viewModel = fakeViewModel)
}
