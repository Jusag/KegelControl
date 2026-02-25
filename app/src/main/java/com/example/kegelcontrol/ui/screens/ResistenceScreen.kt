package com.example.kegelcontrol.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kegelcontrol.R
import com.example.kegelcontrol.ui.components.CustomButton
import com.example.kegelcontrol.viewmodel.CronoViewModel
import kotlinx.coroutines.launch

@Composable
fun ResistenceScreen(
    navController: NavController,
    viewModel: CronoViewModel = viewModel()
) {
    val timeMillis by viewModel.time.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var isPressed by remember { mutableStateOf(false) }

    val customFont = FontFamily(Font(R.font.ltstopwatch_regular))

    val sessionTimes by viewModel.sessionTimes.collectAsState()
    val maxTime by viewModel.maxTime.collectAsState()
    val minTime by viewModel.minTime.collectAsState()
    val avgTime by viewModel.avgTime.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                CustomButton(text = "Volver", onClick = { navController.popBackStack() })
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                        .weight(0.2f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = viewModel.formatTime(timeMillis.toLong()),
                        fontSize = 55.sp,
                        fontFamily = customFont,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.6f)
                        .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(20.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Zona de Trabajo",
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),
                            fontSize = 35.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text("Máximo: ${viewModel.formatTime(maxTime)}", color = MaterialTheme.colorScheme.onSurface, fontSize = 25.sp)
                        Text("Mínimo: ${viewModel.formatTime(minTime)}", color = MaterialTheme.colorScheme.onSurface, fontSize = 25.sp)
                        Text("Promedio: ${viewModel.formatTime(avgTime)}", color = MaterialTheme.colorScheme.onSurface, fontSize = 25.sp)

                        Spacer(modifier = Modifier.height(12.dp))

                        Text("Últimos 5 tiempos:", color = MaterialTheme.colorScheme.onSurface, fontSize = 32.sp)

                        val lastCount = 5
                        val totalCount = sessionTimes.size
                        sessionTimes.takeLast(lastCount).reversed().forEachIndexed { index, time ->
                            val realIndex = totalCount - index
                            Text(
                                text = "$realIndex - ${viewModel.formatTime(time)}",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 30.sp
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                        .weight(0.2f)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    isPressed = true
                                    coroutineScope.launch { viewModel.startCrono() }
                                    tryAwaitRelease()
                                    viewModel.resetCrono()
                                    isPressed = false
                                }
                            )
                        }
                        .background(
                            color = if (isPressed) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(50.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "PRESS",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 35.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}