package com.example.kegelcontrol.viewmodel

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UiViewModel : ViewModel() {
    // Configuraciones maestras de la UI
    private val _bottomNavBarHeight = MutableStateFlow(80.dp)
    val bottomNavBarHeight: StateFlow<Dp> = _bottomNavBarHeight

    private val _buttonHorizontalPadding = MutableStateFlow(32.dp)
    val buttonHorizontalPadding: StateFlow<Dp> = _buttonHorizontalPadding

    // Configuración para botones superiores (Back y Reset)
    private val _topButtonHeight = MutableStateFlow(60.dp)
    val topButtonHeight: StateFlow<Dp> = _topButtonHeight

    private val _topButtonFontSize = MutableStateFlow(18.sp)
    val topButtonFontSize: StateFlow<TextUnit> = _topButtonFontSize

    fun updateBottomBarHeight(newHeight: Dp) {
        _bottomNavBarHeight.value = newHeight
    }
}