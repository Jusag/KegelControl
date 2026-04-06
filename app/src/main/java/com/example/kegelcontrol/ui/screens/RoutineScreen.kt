package com.example.kegelcontrol.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kegelcontrol.R
import com.example.kegelcontrol.ui.Screen
import com.example.kegelcontrol.ui.components.CustomButton
import com.example.kegelcontrol.viewmodel.RoutineStep
import com.example.kegelcontrol.viewmodel.RoutineViewModel
import com.example.kegelcontrol.viewmodel.StepType
import com.example.kegelcontrol.viewmodel.UiViewModel

@Composable
fun RoutineScreen(navController: NavController, viewModel: RoutineViewModel, uiViewModel: UiViewModel) {
    val routineSteps by viewModel.routineSteps.collectAsState()
    
    // Variables para controlar el porcentaje de espacio (weights)
    val listWeight = 0.4f      // Reducido un poco para dar espacio a opciones
    val optionsWeight = 0.35f   // Aumentado para que quepan las 3 opciones
    val startButtonWeight = 0.25f

    // Tamaños estándar desde la clase maestra
    val topButtonHeight by uiViewModel.topButtonHeight.collectAsState()
    val topButtonFontSize by uiViewModel.topButtonFontSize.collectAsState()

    var showResetDialog by remember { mutableStateOf(false) }
    var contractSeconds by remember { mutableStateOf("") }
    var relaxSeconds by remember { mutableStateOf("") }
    var restSeconds by remember { mutableStateOf("") }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Barra Superior: Volver y Reset
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
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
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título
                Text(
                    text = stringResource(R.string.screen_routine),
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 1. Panel Superior: Lista de pasos programados
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(listWeight)
                        .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(16.dp))
                        .padding(8.dp)
                ) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        itemsIndexed(routineSteps) { index, step ->
                            RoutineStepItem(
                                index = index,
                                step = step,
                                isFirst = index == 0,
                                isLast = index == routineSteps.size - 1,
                                onRemove = { viewModel.removeStep(index) },
                                onMoveUp = { viewModel.moveStep(index, index - 1) },
                                onMoveDown = { viewModel.moveStep(index, index + 1) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 2. Panel Central: Añadir pasos (con scroll por si acaso)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(optionsWeight)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    AddStepRow(
                        label = stringResource(R.string.routine_contract),
                        value = contractSeconds,
                        onValueChange = { contractSeconds = it },
                        onAdd = {
                            viewModel.addStep(StepType.CONTRACT, contractSeconds.toIntOrNull() ?: 0)
                            contractSeconds = ""
                        }
                    )
                    AddStepRow(
                        label = stringResource(R.string.routine_relax),
                        value = relaxSeconds,
                        onValueChange = { relaxSeconds = it },
                        onAdd = {
                            viewModel.addStep(StepType.RELAX, relaxSeconds.toIntOrNull() ?: 0)
                            relaxSeconds = ""
                        }
                    )
                    AddStepRow(
                        label = stringResource(R.string.routine_rest),
                        value = restSeconds,
                        onValueChange = { restSeconds = it },
                        onAdd = {
                            viewModel.addStep(StepType.REST, restSeconds.toIntOrNull() ?: 0)
                            restSeconds = ""
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 3. Botón inferior: INICIAR / START
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(startButtonWeight),
                    contentAlignment = Alignment.Center
                ) {
                    CustomButton(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.9f),
                        text = stringResource(R.string.action_start),
                        onClick = { /* Próxima etapa: Ejecución de la rutina */ }
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
                    viewModel.clearRoutine()
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

@Composable
fun AddStepRow(label: String, value: String, onValueChange: (String) -> Unit, onAdd: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, modifier = Modifier.weight(1f), fontSize = 16.sp)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.width(70.dp), // Reducido el ancho del input
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
            placeholder = { Text("0", fontSize = 14.sp) }
        )
        IconButton(onClick = onAdd) {
            Icon(Icons.Default.Add, contentDescription = "Add", tint = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun RoutineStepItem(
    index: Int,
    step: RoutineStep,
    isFirst: Boolean,
    isLast: Boolean,
    onRemove: () -> Unit,
    onMoveUp: () -> Unit,
    onMoveDown: () -> Unit
) {
    val typeText = when (step.type) {
        StepType.CONTRACT -> stringResource(R.string.routine_contract)
        StepType.RELAX -> stringResource(R.string.routine_relax)
        StepType.REST -> stringResource(R.string.routine_rest)
    }

    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${index + 1}. $typeText - ${step.duration} ${stringResource(R.string.routine_seconds_unit)}",
                modifier = Modifier.weight(1f),
                fontSize = 14.sp
            )
            
            Row {
                IconButton(onClick = onMoveUp, enabled = !isFirst, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.ArrowUpward, contentDescription = stringResource(R.string.move_up_description), tint = if (isFirst) Color.Gray else MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = onMoveDown, enabled = !isLast, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.ArrowDownward, contentDescription = stringResource(R.string.move_down_description), tint = if (isLast) Color.Gray else MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = onRemove, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
