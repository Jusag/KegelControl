package com.example.kegelcontrol.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kegelcontrol.R
import com.example.kegelcontrol.ui.screens.CronoScreen
import com.example.kegelcontrol.ui.screens.RoutineScreen
import com.example.kegelcontrol.ui.screens.HomeScreen
import com.example.kegelcontrol.ui.screens.ResistenceScreen
import com.example.kegelcontrol.ui.screens.SettingsScreen
import com.example.kegelcontrol.ui.screens.SplashScreen
import com.example.kegelcontrol.viewmodel.CronoViewModel
import com.example.kegelcontrol.viewmodel.ResistenceViewModel
import com.example.kegelcontrol.viewmodel.RoutineViewModel
import com.example.kegelcontrol.viewmodel.SettingsViewModel
import com.example.kegelcontrol.viewmodel.UiViewModel

sealed class Screen(val route: String, val resourceId: Int) {
    object Splash : Screen("splash", 0)
    object Home : Screen("home", R.string.home_button)
    object Crono : Screen("crono", R.string.screen_crono)
    object Resistence : Screen("resistence", R.string.screen_resistence)
    object Routine : Screen("routine", R.string.screen_routine)
    object Settings : Screen("settings", R.string.settings_title)
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val cronoViewModel: CronoViewModel = viewModel()
    val resistenceViewModel: ResistenceViewModel = viewModel()
    val routineViewModel: RoutineViewModel = viewModel()
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
            CronoScreen(navController = navController, viewModel = cronoViewModel, uiViewModel = uiViewModel)
        }
        composable(Screen.Resistence.route) {
            ResistenceScreen(navController = navController, viewModel = resistenceViewModel, uiViewModel = uiViewModel)
        }
        composable(Screen.Routine.route) {
            RoutineScreen(navController = navController, viewModel = routineViewModel, uiViewModel = uiViewModel)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController, viewModel = settingsViewModel, uiViewModel = uiViewModel)
        }
    }
}