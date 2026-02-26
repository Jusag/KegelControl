package com.example.kegelcontrol.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.kegelcontrol.ui.Screen

@Composable
fun BottomNavBar(navController: NavController, currentRoute: String?) {
    BottomAppBar {
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { if (currentRoute != Screen.Home.route) navController.navigate(Screen.Home.route) }) {
            Icon(Icons.Default.Home, contentDescription = "Home")
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { if (currentRoute != Screen.Settings.route) navController.navigate(Screen.Settings.route) }) {
            Icon(Icons.Default.Settings, contentDescription = "Ajustes")
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}
