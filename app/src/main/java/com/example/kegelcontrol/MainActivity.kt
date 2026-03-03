package com.example.kegelcontrol

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import com.example.kegelcontrol.ui.AppNavigation
import com.example.kegelcontrol.ui.theme.KegelControlTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KegelControlTheme(darkTheme = true) {
                AppNavigation()
            }
        }
    }
}