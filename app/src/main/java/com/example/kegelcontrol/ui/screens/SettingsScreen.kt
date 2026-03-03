package com.example.kegelcontrol.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kegelcontrol.R
import com.example.kegelcontrol.ui.Screen
import com.example.kegelcontrol.ui.components.BottomNavBar
import com.example.kegelcontrol.viewmodel.SettingsViewModel
import com.example.kegelcontrol.viewmodel.UiViewModel

@Composable
fun SettingsScreen(navController: NavController, viewModel: SettingsViewModel, uiViewModel: UiViewModel) {
    val bottomNavBarHeight by uiViewModel.bottomNavBarHeight.collectAsState()
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()
    
    var showDialog by remember { mutableStateOf(false) }
    var pendingLanguageCode by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                currentRoute = Screen.Settings.route,
                modifier = Modifier.height(bottomNavBarHeight)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(stringResource(R.string.settings_title), fontSize = 24.sp, style = MaterialTheme.typography.titleLarge)

            // Opción: Sonido
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(R.string.sound_option), fontSize = 20.sp)
                val isSoundEnabled by viewModel.isSoundEnabled.collectAsState()
                Switch(
                    checked = isSoundEnabled,
                    onCheckedChange = { viewModel.toggleSound() }
                )
            }

            // Opción: Vibración
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(R.string.vibration_option), fontSize = 20.sp)
                val isVibrationEnabled by viewModel.isVibrationEnabled.collectAsState()
                Switch(
                    checked = isVibrationEnabled,
                    onCheckedChange = { viewModel.toggleVibration() }
                )
            }

            Divider()

            // Opción: Idioma
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.language_option), fontSize = 20.sp)
                Spacer(modifier = Modifier.height(8.dp))
                
                ListItem(
                    headlineContent = { Text("Español") },
                    trailingContent = { RadioButton(selected = selectedLanguage == "es" || selectedLanguage == "default", onClick = null) },
                    modifier = Modifier.clickable { 
                        pendingLanguageCode = "es"
                        showDialog = true 
                    }
                )
                ListItem(
                    headlineContent = { Text("English") },
                    trailingContent = { RadioButton(selected = selectedLanguage == "en", onClick = null) },
                    modifier = Modifier.clickable { 
                        pendingLanguageCode = "en"
                        showDialog = true 
                    }
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(R.string.dialog_language_change_title)) },
            text = { Text(stringResource(R.string.dialog_language_change_text)) },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.setLanguage(pendingLanguageCode)
                    showDialog = false
                }) {
                    Text(stringResource(R.string.dialog_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.dialog_cancel))
                }
            }
        )
    }
}
