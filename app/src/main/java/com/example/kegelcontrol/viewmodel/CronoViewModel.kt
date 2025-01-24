package com.example.kegelcontrol.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CronoViewModel : ViewModel() {
    private var cronoJob: Job? = null

    //Lista de Vueltas
    private val _lapsList = MutableStateFlow<List<Long>>(emptyList())
    val lapsList: StateFlow<List<Long>> = _lapsList

    // Estado del tiempo transcurrido
    private val _timeMillis = MutableStateFlow(0)
    val time: StateFlow<Int> = _timeMillis

    //Estado de ejecucion
    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning

    //Control de vueltas realizadas
    private var lapControl: Long = 0

    //Control de Promedio
    var auxProm: Long = 0L

    fun startCrono() {
        if (_isRunning.value) return
        _isRunning.value = true
        cronoJob = viewModelScope.launch {
            while (_isRunning.value) {
                delay(10L)
                _timeMillis.value += 10
            }
        }
    }

    fun pauseCrono() {
        _isRunning.value = false
        cronoJob?.cancel()
    }

    fun resetCrono() {
        pauseCrono()
        _timeMillis.value = 0
        _lapsList.value = emptyList()
        auxProm = 0L
    }

    fun addLap() {
        if (isRunning.value) {
            val actualTime: Long = _timeMillis.value.toLong()
            lapControl = actualTime - lapControl
            if (lapControl < 0) {
                lapControl = 5L
            }

            _lapsList.value = _lapsList.value + lapControl
            lapControl = actualTime

            promGeneral()
        }
    }

    fun formatTime(raw: Long): String {
        val minutes = (raw / 1000) / 60
        val seconds = (raw / 1000) % 60
        val centiseconds = (raw % 1000) / 10
        return "%02d:%02d.%02d".format(minutes, seconds, centiseconds)
    }

    fun promGeneral(){
        auxProm =  lapsList.value.sumOf{it}/lapsList.value.size
    }
}