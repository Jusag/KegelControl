package com.example.kegelcontrol.ui.screens

import android.content.res.Configuration
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kegelcontrol.R
import com.example.kegelcontrol.ui.components.CustomButton
import com.example.kegelcontrol.ui.theme.KegelControlTheme
import com.example.kegelcontrol.ui.util.getAdaptiveFontSize
import com.example.kegelcontrol.viewmodel.CronoViewModel
import kotlinx.coroutines.launch

@Composable
fun ResistenceScreen(
    navController: NavController,
    viewModel: CronoViewModel
) {
    DisposableEffect(Unit) {
        onDispose {
            viewModel.pauseCrono()
        }
    }

    val timeMillis by viewModel.time.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var isPressed by remember { mutableStateOf(false) }

    val customFont = FontFamily(Font(R.font.ltstopwatch_regular))

    val sessionTimes by viewModel.sessionTimes.collectAsState()
    val maxTime by viewModel.maxTime.collectAsState()
    val minTime by viewModel.minTime.collectAsState()
    val avgTime by viewModel.avgTime.collectAsState()

    val configuration = LocalConfiguration.current

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
                        fontSize = getAdaptiveFontSize(portraitSize = 55.sp),
                        fontFamily = customFont,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                // Tarjeta de estadísticas
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.6f)
                        .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(20.dp))
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top, // Forzar alineación en la parte superior
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text("Máximo: ${viewModel.formatTime(maxTime)}", color = MaterialTheme.colorScheme.onSurface, fontSize = getAdaptiveFontSize(portraitSize = 25.sp))
                            Text("Mínimo: ${viewModel.formatTime(minTime)}", color = MaterialTheme.colorScheme.onSurface, fontSize = getAdaptiveFontSize(portraitSize = 25.sp))
                            Text("Promedio: ${viewModel.formatTime(avgTime)}", color = MaterialTheme.colorScheme.onSurface, fontSize = getAdaptiveFontSize(portraitSize = 25.sp))
                        }
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Máximo: ${viewModel.formatTime(maxTime)}", color = MaterialTheme.colorScheme.onSurface, fontSize = getAdaptiveFontSize(portraitSize = 25.sp))
                            Text("Mínimo: ${viewModel.formatTime(minTime)}", color = MaterialTheme.colorScheme.onSurface, fontSize = getAdaptiveFontSize(portraitSize = 25.sp))
                            Text("Promedio: ${viewModel.formatTime(avgTime)}", color = MaterialTheme.colorScheme.onSurface, fontSize = getAdaptiveFontSize(portraitSize = 25.sp))
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    val lastCount = 5
                    val totalCount = sessionTimes.size
                    sessionTimes.takeLast(lastCount).reversed().forEachIndexed { index, time ->
                        val realIndex = totalCount - index
                        Text(
                            text = "$realIndex - ${viewModel.formatTime(time)}",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = getAdaptiveFontSize(portraitSize = 30.sp)
                        )
                    }
                }

                // Botón PRESS
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
                        fontSize = getAdaptiveFontSize(portraitSize = 35.sp),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewResistenceScreen() {
    val fakeViewModel = com.example.kegelcontrol.viewmodel.CronoViewModel()
    KegelControlTheme {
        ResistenceScreen(navController = rememberNavController(), viewModel = fakeViewModel)
    }
}