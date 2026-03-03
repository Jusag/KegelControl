package com.example.kegelcontrol.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kegelcontrol.R
import com.example.kegelcontrol.ui.screens.CronoScreen
import com.example.kegelcontrol.ui.screens.DetailScreen
import com.example.kegelcontrol.ui.screens.HomeScreen
import com.example.kegelcontrol.ui.screens.ResistenceScreen
import com.example.kegelcontrol.ui.screens.SettingsScreen
import com.example.kegelcontrol.ui.screens.SplashScreen
import com.example.kegelcontrol.viewmodel.CronoViewModel
import com.example.kegelcontrol.viewmodel.SettingsViewModel
import com.example.kegelcontrol.viewmodel.UiViewModel

sealed class Screen(val route: String, val resourceId: Int) {
    object Splash : Screen("splash", 0) // Splash no necesita recurso de texto
    object Home : Screen("home", R.string.home_button)
    object Crono : Screen("crono", R.string.screen_crono)
    object Resistence : Screen("resistence", R.string.screen_resistence)
    object Detail : Screen("detail", R.string.screen_detail)
    object Settings : Screen("settings", R.string.settings_title)
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val cronoViewModel: CronoViewModel = viewModel()
    val settingsViewModel: SettingsViewModel = viewModel()
    val uiViewModel: UiViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController = navController, uiViewModel = uiViewModel)
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
            SettingsScreen(navController = navController, viewModel = settingsViewModel, uiViewModel = uiViewModel)
        }
    }
}