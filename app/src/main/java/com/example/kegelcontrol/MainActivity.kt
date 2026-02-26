package com.example.kegelcontrol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.kegelcontrol.ui.AppNavigation
import com.example.kegelcontrol.ui.theme.KegelControlTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KegelControlTheme(darkTheme = true) {
                AppNavigation()
            }
        }
    }
}