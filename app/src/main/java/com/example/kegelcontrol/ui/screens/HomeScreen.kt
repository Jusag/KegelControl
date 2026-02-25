package com.example.kegelcontrol.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kegelcontrol.ui.Screen
import com.example.kegelcontrol.ui.components.CustomButton
import com.example.kegelcontrol.viewmodel.ThemeViewModel

@Composable
fun HomeScreen(navController: NavController, themeViewModel: ThemeViewModel) {
    val screens = listOf(
        Screen.Crono,
        Screen.Resistence,
        Screen.Detail
    )
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Module buttons take up most of the space
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                screens.forEachIndexed { index, screen ->
                    CustomButton(
                        modifier = Modifier.size(120.dp),
                        text = screen.route.replaceFirstChar { it.uppercase() },
                        onClick = { navController.navigate(screen.route) }
                    )
                    if (index < screens.lastIndex) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            // Theme switch at the bottom
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Dark Theme")
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = { themeViewModel.toggleTheme() }
                )
            }
        }
    }
}