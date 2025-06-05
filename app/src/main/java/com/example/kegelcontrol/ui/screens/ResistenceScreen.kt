import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kegelcontrol.R
import com.example.kegelcontrol.viewmodel.CronoViewModel
import kotlinx.coroutines.launch

@Composable
fun ResistenceScreen(
    viewModel: CronoViewModel = viewModel()
) {
    val timeMillis by viewModel.time.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var isPressed by remember { mutableStateOf(false) }

    // Fuente personalizada
    val customFont = FontFamily(Font(R.font.ltstopwatch_regular))

    val sessionTimes by viewModel.sessionTimes.collectAsState()
    val maxTime by viewModel.maxTime.collectAsState()
    val minTime by viewModel.minTime.collectAsState()
    val avgTime by viewModel.avgTime.collectAsState()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF1E1E1E), Color(0xFF121212)))) // Fondo degradado
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔹 CRONÓMETRO
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .weight(0.2f), // 20% de la pantalla
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = viewModel.formatTime(timeMillis.toLong()),
                fontSize = 50.sp,
                fontFamily = customFont,
                color = Color.White
            )
        }

        // 🔹 CAJA CENTRAL (PUEDE USARSE PARA ANIMACIONES)
        /*
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f) // 60% de la pantalla
                .background(Color(0xFF2A2A2A), shape = RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Zona de Trabajo",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 20.sp
            )
        }
*/
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f) // Altura fija
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
                    text = "Zona de Trabajo",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 35.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text("Máximo: ${viewModel.formatTime(maxTime)}", color = Color.White, fontSize = 25.sp)
                Text("Mínimo: ${viewModel.formatTime(minTime)}", color = Color.White, fontSize = 25.sp)
                Text("Promedio: ${viewModel.formatTime(avgTime)}", color = Color.White, fontSize = 25.sp)

                Spacer(modifier = Modifier.height(12.dp))

                Text("Últimos 5 tiempos:", color = Color.White, fontSize = 32.sp)

                val lastCount = 5
                val totalCount = sessionTimes.size
                sessionTimes.takeLast(lastCount).reversed().forEachIndexed { index, time ->
                    val realIndex = totalCount - index
                    Text(
                        text = "$realIndex - ${viewModel.formatTime(time)}",
                        color = Color.White,
                        fontSize = 30.sp
                    )
                }
            }
        }


        // 🔹 BOTÓN "PRESS"
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .weight(0.2f) // 20% de la pantalla
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
                    color = if (isPressed) Color(0xFF4E2A8E) else Color(0xFF653AAC),
                    shape = RoundedCornerShape(50.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "PRESS",
                color = Color.White,
                fontSize = 35.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
