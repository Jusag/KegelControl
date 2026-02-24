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
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kegelcontrol.R
import com.example.kegelcontrol.ui.components.CustomButton
import com.example.kegelcontrol.viewmodel.CronoViewModel

@Composable
fun CronoScreen(
    navController: NavController,
    viewModel: CronoViewModel = viewModel()
) {
    val time by viewModel.time.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()
    val lapsList by viewModel.lapsList.collectAsState()

    var buttonTextStartPause by remember { mutableStateOf("Start") }

    val customFont = FontFamily(Font(R.font.ltstopwatch_regular))

    Scaffold {
        innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Button(onClick = { navController.popBackStack() }) {
                Text("Volver")
            }
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
                        text = viewModel.formatTime(time.toLong()),
                        fontSize = 55.sp,
                        fontFamily = customFont,
                        color = Color.White
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.6f)
                        .background(Color(0xFF2A2A2A), shape = RoundedCornerShape(20.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Vueltas recientes",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 35.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            itemsIndexed(lapsList.takeLast(10).reversed()) { index, lap ->
                                Text(
                                    "Vuelta ${lapsList.size - index} - Tiempo: ${viewModel.formatTime(lap)}",
                                    color = Color.White,
                                    fontSize = 25.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        if (lapsList.isNotEmpty()) {
                            Text(
                                "Promedio: ${viewModel.formatTime(viewModel.auxProm)}",
                                color = Color.White,
                                fontSize = 30.sp
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.2f)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomButton(
                        text = buttonTextStartPause,
                        onClick = {
                            if (!isRunning) {
                                viewModel.startCrono()
                                buttonTextStartPause = "Pause"
                            } else {
                                viewModel.pauseCrono()
                                buttonTextStartPause = "Start"
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
                        onClick = {
                            viewModel.resetCrono()
                            buttonTextStartPause = "Start"
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCronoScreen() {
    val fakeViewModel = remember { CronoViewModel() }
    CronoScreen(navController = rememberNavController(), viewModel = fakeViewModel)
}