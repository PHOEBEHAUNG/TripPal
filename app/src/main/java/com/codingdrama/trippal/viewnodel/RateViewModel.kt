package com.codingdrama.trippal.viewnodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingdrama.trippal.data.CurrencyEnum
import com.codingdrama.trippal.model.network.data.CurrencyDetailsResponse
import com.codingdrama.trippal.repository.CurrencyRateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RateViewModel(private val repository: CurrencyRateRepository) : ViewModel() {
    companion object {
        private const val TAG = "RateViewModel"
    }

    private val _lastUpdateTime = MutableStateFlow(0L)
    val lastUpdateTime: StateFlow<Long> = _lastUpdateTime.asStateFlow()

    // You must use [SavedStateHandle]/[StateFlow with collectAsState] if you need to save and restore state across configuration changes
    private var _supportedCurrencies: MutableStateFlow<CurrencyDetailsResponse?> = MutableStateFlow(null)
    val supportedCurrencies: StateFlow<CurrencyDetailsResponse?> = _supportedCurrencies

    private var _supportedCurrenciesList: MutableStateFlow<List<String>?> = MutableStateFlow(null)
    val supportedCurrenciesList: StateFlow<List<String>?> = _supportedCurrenciesList

    private val _currentBaseCurrency = MutableStateFlow(CurrencyEnum.USD.name)
    val currentBaseCurrency: StateFlow<String> = _currentBaseCurrency

    private val _currencyRates = MutableStateFlow<Map<String, Float>>(mutableMapOf())
    val currencyRates: StateFlow<Map<String, Float>> = _currencyRates

    init {
        // create a task to getFlightInfo every 10 seconds
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                if (System.currentTimeMillis() - lastUpdateTime.value < 10000) {
//                    Log.d(TAG, "Skipping fetch, last update was less than 10 seconds ago.")
                    continue
                }

                Log.d(TAG, "Fetching Rate info...")
                _lastUpdateTime.value = System.currentTimeMillis()
                _supportedCurrencies.value = repository.getSupportedCurrencies()
                _supportedCurrenciesList.value = supportedCurrencies.value?.data?.keys?.toList() ?: emptyList()
                _currencyRates.value = repository.getAllLatestCurrencyRates(_currentBaseCurrency.value)?.data ?: mutableMapOf()
                delay(10000) // 10 seconds
            }
        }
    }

    fun setDefaultBaseCurrency(currency: String) {
        _currentBaseCurrency.value = currency
    }

    fun updateCurrencyRates() {
        viewModelScope.launch(Dispatchers.IO) {
            _lastUpdateTime.value = System.currentTimeMillis()
            val response = repository.getAllLatestCurrencyRates(_currentBaseCurrency.value)
            if (response != null) {
                _currencyRates.value = response.data
            } else {
                _currencyRates.value = mutableMapOf()
            }
        }
    }
}