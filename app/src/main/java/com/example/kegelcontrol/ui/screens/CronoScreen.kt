package com.example.kegelcontrol.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun CronoScreen(
    navController: NavController,
    viewModel: CronoViewModel
) {
    DisposableEffect(Unit) {
        onDispose {
            viewModel.pauseCrono()
        }
    }

    val time by viewModel.time.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()
    val lapsList by viewModel.lapsList.collectAsState()

    var buttonTextStartPause by remember { mutableStateOf(if (isRunning) "Pause" else "Start") }

    LaunchedEffect(isRunning) {
        buttonTextStartPause = if (isRunning) "Pause" else "Start"
    }

    val customFont = FontFamily(Font(R.font.ltstopwatch_regular))
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
                        fontSize = getAdaptiveFontSize(portraitSize = 55.sp),
                        fontFamily = customFont,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.6f)
                        .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(20.dp))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (lapsList.isNotEmpty()) {
                        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    "Promedio: ${viewModel.formatTime(viewModel.auxProm)}",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = getAdaptiveFontSize(portraitSize = 30.sp)
                                )
                            }
                        } else {
                            Text(
                                "Promedio: ${viewModel.formatTime(viewModel.auxProm)}",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = getAdaptiveFontSize(portraitSize = 30.sp)
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        itemsIndexed(lapsList.takeLast(10).reversed()) { index, lap ->
                            Text(
                                "Vuelta ${lapsList.size - index} - Tiempo: ${viewModel.formatTime(lap)}",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = getAdaptiveFontSize(portraitSize = 25.sp)
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
                        onClick = {
                            viewModel.resetCrono()
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
    val fakeViewModel = com.example.kegelcontrol.viewmodel.CronoViewModel()
    KegelControlTheme {
        CronoScreen(navController = rememberNavController(), viewModel = fakeViewModel)
    }
}