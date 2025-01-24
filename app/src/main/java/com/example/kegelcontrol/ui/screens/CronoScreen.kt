package com.example.kegelcontrol.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kegelcontrol.ui.components.CustomButton
import com.example.kegelcontrol.viewmodel.CronoViewModel


@Composable
fun CronoScreen(
    viewModel: CronoViewModel = viewModel(),
    cronoViewModel: CronoViewModel = viewModel()
) {
    val time by viewModel.time.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()
    var buttonTextStartPause by remember { mutableStateOf("Start") }
    val lapsList by viewModel.lapsList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally // Deja centrado los elementos contenidos
    ) {
        Text(cronoViewModel.formatTime(time.toLong()))

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //Apartado de botones
            CustomButton(
                text = "$buttonTextStartPause",
                onClick = {
                    if (!isRunning) {
                        cronoViewModel.startCrono()
                        buttonTextStartPause = "Pause"
                    } else {
                        cronoViewModel.pauseCrono()
                        buttonTextStartPause = "Start"
                    }
                }
            )

            Spacer(modifier = Modifier.width(10.dp))
            CustomButton(
                text = "Lap",
                onClick = {
                    cronoViewModel.addLap()
                }
            )

            Spacer(modifier = Modifier.width(10.dp))
            CustomButton(text = "Reset",
                onClick = {
                    cronoViewModel.resetCrono()
                    buttonTextStartPause = "Start"
                })
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(7.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                itemsIndexed(lapsList.takeLast(10).reversed()) { index, lap ->
                    Text("Vuelta ${lapsList.size - index} - Tiempo: ${viewModel.formatTime(lap.toLong())}")
                }
            }
            if (lapsList.size > 0) {
                Text(
                    "Promedio: " + viewModel.formatTime(viewModel.auxProm),
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevieCronoScreen()
    {
        val fakeViewModel = remember { CronoViewModel() }
        CronoScreen(viewModel = fakeViewModel, cronoViewModel = fakeViewModel)
    }