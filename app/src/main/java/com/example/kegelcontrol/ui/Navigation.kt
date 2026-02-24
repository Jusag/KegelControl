package com.example.kegelcontrol.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kegelcontrol.ui.screens.CronoScreen
import com.example.kegelcontrol.ui.screens.DetailScreen
import com.example.kegelcontrol.ui.screens.HomeScreen
import com.example.kegelcontrol.ui.screens.ResistenceScreen
import com.example.kegelcontrol.viewmodel.ThemeViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Crono : Screen("crono")
    object Resistence : Screen("resistence")
    object Detail : Screen("detail")
}

@Composable
fun AppNavigation(themeViewModel: ThemeViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController, themeViewModel = themeViewModel)
        }
        composable(Screen.Crono.route) {
            CronoScreen(navController = navController)
        }
        composable(Screen.Resistence.route) {
            ResistenceScreen(navController = navController)
        }
        composable(Screen.Detail.route) {
            DetailScreen(navController = navController)
        }
    }
}