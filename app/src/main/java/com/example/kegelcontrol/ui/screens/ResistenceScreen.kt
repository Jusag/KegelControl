package com.example.kegelcontrol.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
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
import com.example.kegelcontrol.ui.util.getAdaptiveFontSize
import com.example.kegelcontrol.viewmodel.ResistenceViewModel
import com.example.kegelcontrol.viewmodel.UiViewModel
import kotlinx.coroutines.launch

@Composable
fun ResistenceScreen(
    navController: NavController,
    viewModel: ResistenceViewModel,
    uiViewModel: UiViewModel
) {
    DisposableEffect(Unit) {
        onDispose {
            viewModel.pauseCrono()
        }
    }

    val timeMillis by viewModel.time.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var isPressed by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }

    val topButtonHeight by uiViewModel.topButtonHeight.collectAsState()
    val topButtonFontSize by uiViewModel.topButtonFontSize.collectAsState()

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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomButton(
                    modifier = Modifier.height(topButtonHeight),
                    text = stringResource(R.string.back_button),
                    onClick = { navController.popBackStack() },
                    fontSize = topButtonFontSize
                )
                CustomButton(
                    modifier = Modifier.height(topButtonHeight),
                    text = stringResource(R.string.action_reset),
                    onClick = { showResetDialog = true },
                    fontSize = topButtonFontSize
                )
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
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.6f),
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text("${stringResource(R.string.stat_max)}: ${viewModel.formatTime(maxTime)}", color = MaterialTheme.colorScheme.onSurface, fontSize = getAdaptiveFontSize(portraitSize = 25.sp))
                                Text("${stringResource(R.string.stat_min)}: ${viewModel.formatTime(minTime)}", color = MaterialTheme.colorScheme.onSurface, fontSize = getAdaptiveFontSize(portraitSize = 25.sp))
                                Text("${stringResource(R.string.stat_avg)}: ${viewModel.formatTime(avgTime)}", color = MaterialTheme.colorScheme.onSurface, fontSize = getAdaptiveFontSize(portraitSize = 25.sp))
                            }
                        } else {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("${stringResource(R.string.stat_max)}: ${viewModel.formatTime(maxTime)}", color = MaterialTheme.colorScheme.onSurface, fontSize = getAdaptiveFontSize(portraitSize = 25.sp))
                                Text("${stringResource(R.string.stat_min)}: ${viewModel.formatTime(minTime)}", color = MaterialTheme.colorScheme.onSurface, fontSize = getAdaptiveFontSize(portraitSize = 25.sp))
                                Text("${stringResource(R.string.stat_avg)}: ${viewModel.formatTime(avgTime)}", color = MaterialTheme.colorScheme.onSurface, fontSize = getAdaptiveFontSize(portraitSize = 25.sp))
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            itemsIndexed(sessionTimes.reversed()) { index, time ->
                                Text(
                                    text = "${stringResource(R.string.repetition_label)} ${sessionTimes.size - index}: ${viewModel.formatTime(time)}",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = getAdaptiveFontSize(portraitSize = 30.sp)
                                )
                            }
                        }
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
                            shape = RoundedCornerShape(16.dp) // Cambiado de 50.dp a 16.dp para consistencia
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.action_press),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = getAdaptiveFontSize(portraitSize = 35.sp),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text(stringResource(R.string.dialog_reset_title)) },
            text = { Text(stringResource(R.string.dialog_reset_text)) },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.hardReset()
                    showResetDialog = false
                }) {
                    Text(stringResource(R.string.dialog_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text(stringResource(R.string.dialog_cancel))
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewResistenceScreen() {
    KegelControlTheme {
        ResistenceScreen(
            navController = rememberNavController(), 
            viewModel = viewModel(), 
            uiViewModel = viewModel()
        )
    }
}
