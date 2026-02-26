package com.example.kegelcontrol.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kegelcontrol.ui.screens.CronoScreen
import com.example.kegelcontrol.ui.screens.DetailScreen
import com.example.kegelcontrol.ui.screens.HomeScreen
import com.example.kegelcontrol.ui.screens.ResistenceScreen
import com.example.kegelcontrol.ui.screens.SettingsScreen
import com.example.kegelcontrol.viewmodel.CronoViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Crono : Screen("crono")
    object Resistence : Screen("resistence")
    object Detail : Screen("detail")
    object Settings : Screen("settings")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val cronoViewModel: CronoViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Crono.route) {
            CronoScreen(navController = navController, viewModel = cronoViewModel)
        }
        composable(Screen.Resistence.route) {
            ResistenceScreen(navController = navController, viewModel = cronoViewModel)
        }
        composable(Screen.Detail.route) {
            DetailScreen(navController = navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
    }
}