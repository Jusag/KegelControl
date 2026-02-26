package com.example.kegelcontrol.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kegelcontrol.ui.Screen
import com.example.kegelcontrol.ui.components.BottomNavBar
import com.example.kegelcontrol.ui.components.CustomButton

@Composable
fun HomeScreen(navController: NavController) {
    val screens = listOf(
        Screen.Crono,
        Screen.Resistence,
        Screen.Detail
    )

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController, currentRoute = Screen.Home.route) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
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
    }
}