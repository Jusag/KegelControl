package com.example.kegelcontrol.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
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
    
    var showResetDialog by remember { mutableStateOf(false) }

    val customFont = FontFamily(Font(R.font.ltstopwatch_regular))
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
                CustomButton(text = stringResource(R.string.back_button), onClick = { navController.popBackStack() })
                CustomButton(text = stringResource(R.string.action_reset), onClick = { showResetDialog = true })
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
                        Text(
                            "${stringResource(R.string.stat_avg)}: ${viewModel.formatTime(viewModel.auxProm)}",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = getAdaptiveFontSize(portraitSize = 30.sp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        itemsIndexed(lapsList.takeLast(10).reversed()) { index, lap ->
                            Text(
                                "${stringResource(R.string.stat_lap)} ${lapsList.size - index} - ${stringResource(R.string.stat_time)}: ${viewModel.formatTime(lap)}",
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
                        text = if (isRunning) stringResource(R.string.action_pause) else stringResource(R.string.action_start),
                        onClick = {
                            if (!isRunning) {
                                viewModel.startCrono()
                            } else {
                                viewModel.pauseCrono()
                            }
                        }
                    )

                    CustomButton(
                        text = stringResource(R.string.action_lap),
                        onClick = {
                            viewModel.addLap()
                        }
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
                    viewModel.resetCrono()
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
fun PreviewCronoScreen() {
    val fakeViewModel = com.example.kegelcontrol.viewmodel.CronoViewModel()
    KegelControlTheme {
        CronoScreen(navController = rememberNavController(), viewModel = fakeViewModel)
    }
}