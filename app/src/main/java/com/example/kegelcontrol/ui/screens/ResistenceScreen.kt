package com.example.kegelcontrol.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kegelcontrol.R
import com.example.kegelcontrol.viewmodel.CronoViewModel
import kotlinx.coroutines.launch

@Composable
fun ResistenceScreen(
    viewModel: CronoViewModel = viewModel(),
    cronoViewModel: CronoViewModel = viewModel()


) {
    val timeMillis by viewModel.time.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()
    val coroutine = rememberCoroutineScope()
    val customFont = FontFamily(
        Font(R.font.ltstopwatch_regular)
    )
    var isPressed by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4F), //ESTOS TIENE EL 40%
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = viewModel.formatTime(timeMillis.toLong()),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = customFont,
                    fontSize = 35.sp
                ),
                modifier = Modifier
                    .padding(bottom = 2.dp)
            )
        }

        /*
        Spacer(
            modifier = Modifier
                .padding(8.dp)
        ) //Separadoooooor
        */


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6F)//ESTO TIENE EL 60%
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                isPressed = true
                                coroutine.launch { viewModel.startCrono() }
                                tryAwaitRelease()
                                viewModel.pauseCrono()
                                isPressed = false
                            }
                        )
                    }
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (isPressed) Color(0xFF653AAC) else Color(0xFF6750A4)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "PRESS",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun PrevieResistenceScreen() {
    ResistenceScreen()
}