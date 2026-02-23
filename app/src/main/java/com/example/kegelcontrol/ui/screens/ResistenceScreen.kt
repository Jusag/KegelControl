package com.example.kegelcontrol.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kegelcontrol.R
import com.example.kegelcontrol.ui.components.CustomButton
import com.example.kegelcontrol.ui.components.scaledSp
import com.example.kegelcontrol.viewmodel.CronoViewModel

@Composable
fun ResistenceScreen(
    onBack: () -> Unit = {},
    viewModel: CronoViewModel = viewModel()
) {
    // Aseguramos que time sea Long. Si da error, usa: (viewModel.time.collectAsState().value).toLong()
    val time by viewModel.time.collectAsState()
    val lapsList by viewModel.lapsList.collectAsState()

    val last10Laps = remember(lapsList) { lapsList.takeLast(10) }
    val avgLast10 = remember(last10Laps) {
        if (last10Laps.isNotEmpty()) last10Laps.average().toLong() else 0L
    }

    val customFont = FontFamily(Font(R.font.ltstopwatch_regular))
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(targetValue = if (isPressed) 0.88f else 1f, label = "btnScale")
    val buttonColor by animateColorAsState(
        targetValue = if (isPressed) Color(0xFF00E5FF) else Color(0xFF444444),
        label = "btnColor"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF1A1A1A), Color(0xFF000000))))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().weight(0.15f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomButton(text = "←", onClick = onBack)
            Text(
                text = viewModel.formatTime(time.toLong()),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = scaledSp(55),
                fontFamily = customFont,
                color = if (isPressed) Color.White else Color.Cyan
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.55f)
                .background(Color(0xFF222222), shape = RoundedCornerShape(24.dp))
                .padding(20.dp)
        ) {
            Column {
                Text(
                    "HISTORIAL RESISTENCIA",
                    color = Color.Cyan,
                    fontSize = scaledSp(18),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(15.dp))

                if (lapsList.isEmpty()) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Mantén presionado para registrar tu primer tiempo",
                            color = Color.Gray, textAlign = TextAlign.Center)
                    }
                } else {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        itemsIndexed(last10Laps.reversed()) { index, lap ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Intento ${lapsList.size - index}", color = Color.Gray)
                                // CORRECCIÓN: lap.toLong()
                                Text(viewModel.formatTime(lap.toLong()), color = Color.White, fontFamily = customFont)
                            }
                        }
                    }

                    Box(Modifier.fillMaxWidth().height(1.dp).background(Color.DarkGray).padding(vertical = 8.dp))

                    Text(
                        // CORRECCIÓN: avgLast10 ya es Long por el .toLong() de arriba
                        text = "PROMEDIO: ${viewModel.formatTime(avgLast10)}",
                        color = Color.Yellow,
                        fontSize = scaledSp(26),
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth().weight(0.3f),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .graphicsLayer(scaleX = scale, scaleY = scale)
                    .background(buttonColor, shape = RoundedCornerShape(80.dp))
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                awaitFirstDown()
                                isPressed = true
                                viewModel.startCrono()

                                waitForUpOrCancellation()

                                isPressed = false
                                viewModel.addLap()
                                viewModel.resetCrono()
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isPressed) "¡RESISTE!" else "MANTÉN",
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = scaledSp(20)
                )
            }
        }
    }
}