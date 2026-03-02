package com.example.kegelcontrol.viewmodel

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UiViewModel : ViewModel() {
    // Configuraciones maestras de la UI
    private val _bottomNavBarHeight = MutableStateFlow(80.dp)
    val bottomNavBarHeight: StateFlow<Dp> = _bottomNavBarHeight

    private val _buttonHorizontalPadding = MutableStateFlow(32.dp)
    val buttonHorizontalPadding: StateFlow<Dp> = _buttonHorizontalPadding

    // Funciones para cambiar dimensiones dinámicamente si fuera necesario
    fun updateBottomBarHeight(newHeight: Dp) {
        _bottomNavBarHeight.value = newHeight
    }
}