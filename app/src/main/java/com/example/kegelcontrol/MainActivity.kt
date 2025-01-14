package com.example.kegelcontrol

import android.os.Bundle
import androidx.activity.ComponentActivity //Clase para usar Jetpack Compose
import androidx.activity.compose.setContent
import com.example.kegelcontrol.ui.screens.HomeScreen
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text //Text de Jetpack Compose
//import androidx.compose.runtime.Composable //NO utilizo Composable desde el Main...modularizo desde los demas

class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent{
            HomeScreen()


            }
        }
}
