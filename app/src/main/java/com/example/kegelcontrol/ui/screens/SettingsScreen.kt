package com.example.kegelcontrol.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kegelcontrol.ui.Screen
import com.example.kegelcontrol.ui.components.BottomNavBar
import com.example.kegelcontrol.viewmodel.SettingsViewModel
import com.example.kegelcontrol.viewmodel.UiViewModel

@Composable
fun SettingsScreen(navController: NavController, viewModel: SettingsViewModel, uiViewModel: UiViewModel) {
    // Obtenemos la altura de la barra desde la clase maestra
    val bottomNavBarHeight by uiViewModel.bottomNavBarHeight.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                currentRoute = Screen.Settings.route,
                modifier = Modifier.height(bottomNavBarHeight)
            )
        }
    ) {
        innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Sonido", fontSize = 20.sp)
                val isSoundEnabled by viewModel.isSoundEnabled.collectAsState()
                Switch(
                    checked = isSoundEnabled,
                    onCheckedChange = { viewModel.toggleSound() }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Vibración", fontSize = 20.sp)
                val isVibrationEnabled by viewModel.isVibrationEnabled.collectAsState()
                Switch(
                    checked = isVibrationEnabled,
                    onCheckedChange = { viewModel.toggleVibration() }
                )
            }
        }
    }
}