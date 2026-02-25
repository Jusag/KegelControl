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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.kegelcontrol.ui.theme.KegelControlTheme
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
                        .weight(0.2f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = viewModel.formatTime(time.toLong()),
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
                            text = "Vueltas recientes",
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),
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
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 25.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        if (lapsList.isNotEmpty()) {
                            Text(
                                "Promedio: ${viewModel.formatTime(viewModel.auxProm)}",
                                color = MaterialTheme.colorScheme.onSurface,
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
    KegelControlTheme {
        CronoScreen(navController = rememberNavController(), viewModel = fakeViewModel)
    }
}