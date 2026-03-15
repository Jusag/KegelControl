package com.example.kegelcontrol.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class RoutineStep(val type: StepType, val duration: Int)

enum class StepType {
    CONTRACT,
    RELAX,
    REST
}

class RoutineViewModel : ViewModel() {

    private val _routineSteps = MutableStateFlow<List<RoutineStep>>(emptyList())
    val routineSteps: StateFlow<List<RoutineStep>> = _routineSteps.asStateFlow()

    fun addStep(stepType: StepType, duration: Int) {
        if (duration > 0) {
            _routineSteps.value = _routineSteps.value + RoutineStep(type = stepType, duration = duration)
        }
    }

    fun removeStep(index: Int) {
        if (index in _routineSteps.value.indices) {
            _routineSteps.value = _routineSteps.value.toMutableList().also { it.removeAt(index) }
        }
    }

    fun moveStep(fromIndex: Int, toIndex: Int) {
        val currentList = _routineSteps.value.toMutableList()
        if (fromIndex in currentList.indices && toIndex in currentList.indices) {
            val item = currentList.removeAt(fromIndex)
            currentList.add(toIndex, item)
            _routineSteps.value = currentList
        }
    }

    fun clearRoutine() {
        _routineSteps.value = emptyList()
    }
}