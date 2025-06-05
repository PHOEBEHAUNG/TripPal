package com.codingdrama.trippal.viewnodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingdrama.trippal.model.network.data.InstantSchedules
import com.codingdrama.trippal.repository.FlightInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FlightMainViewModel (private val repository: FlightInfoRepository) : ViewModel() {
    companion object {
        private const val TAG = "FlightMainViewModel"
    }
    private val _lastUpdateTime = MutableStateFlow(0L)
    val lastUpdateTime: StateFlow<Long> = _lastUpdateTime.asStateFlow()

    private val _instantSchedules = MutableStateFlow<InstantSchedules?>(null)
    val instantSchedules: StateFlow<InstantSchedules?> = _instantSchedules.asStateFlow()

    init {
        // create a task to getFlightInfo every 10 seconds
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                if (System.currentTimeMillis() - lastUpdateTime.value < 10000) {
//                    Log.d(TAG, "Skipping fetch, last update was less than 10 seconds ago.")
                    continue
                }

                Log.d(TAG, "Fetching flight info...")
                getFlightInfo()
                delay(10000) // 10 seconds
            }
        }
    }

    fun getFlightInfo() {
        viewModelScope.launch {
            _lastUpdateTime.value = System.currentTimeMillis()
            _instantSchedules.value = repository.getFlightInfoArrive()
            Log.d(TAG, "Flight Info: ${_instantSchedules.value?.instantSchedules?.size}")
        }
    }
}