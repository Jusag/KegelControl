package com.example.kegelcontrol.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kegelcontrol.ui.Screen
import com.example.kegelcontrol.ui.components.BottomNavBar
import com.example.kegelcontrol.ui.components.CustomButton
import com.example.kegelcontrol.viewmodel.UiViewModel

@Composable
fun HomeScreen(navController: NavController, uiViewModel: UiViewModel) {
    val screens = listOf(
        Screen.Crono,
        Screen.Resistence,
        Screen.Routine // Cambiado de Detail a Routine
    )

    val bottomNavBarHeight by uiViewModel.bottomNavBarHeight.collectAsState()
    val buttonHorizontalPadding by uiViewModel.buttonHorizontalPadding.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                currentRoute = Screen.Home.route,
                modifier = Modifier.height(bottomNavBarHeight)
            )
        }
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = buttonHorizontalPadding)
                        .height(80.dp),
                    text = stringResource(screen.resourceId),
                    onClick = { navController.navigate(screen.route) }
                )
                if (index < screens.lastIndex) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}