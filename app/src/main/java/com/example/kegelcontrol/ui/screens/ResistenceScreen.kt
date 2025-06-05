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
