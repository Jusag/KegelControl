package com.example.kegelcontrol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.kegelcontrol.ui.AppNavigation
import com.example.kegelcontrol.ui.theme.KegelControlTheme
import com.example.kegelcontrol.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {
    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
            KegelControlTheme(darkTheme = isDarkTheme) {
                AppNavigation(themeViewModel = themeViewModel)
            }
        }
    }
}