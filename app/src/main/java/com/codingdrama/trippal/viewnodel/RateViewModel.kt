package com.codingdrama.trippal.viewnodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.lifecycle.ViewModel

class RateViewModel : ViewModel() {
    private val _currentCash = mutableFloatStateOf(0.0f)
    val currentCash: State<Float> = _currentCash

    fun updateCurrentCash(value: Float) {
        _currentCash.floatValue = value
    }
}