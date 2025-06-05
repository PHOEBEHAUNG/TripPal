package com.codingdrama.trippal.viewnodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingdrama.trippal.data.CurrencyEnum
import com.codingdrama.trippal.model.network.data.CurrencyDetailsResponse
import com.codingdrama.trippal.repository.CurrencyRateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RateViewModel(private val repository: CurrencyRateRepository) : ViewModel() {
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
        viewModelScope.launch(Dispatchers.IO) {
            _supportedCurrencies.value = repository.getSupportedCurrencies()
            _supportedCurrenciesList.value = supportedCurrencies.value?.data?.keys?.toList() ?: emptyList()
            _currencyRates.value = repository.getAllLatestCurrencyRates(_currentBaseCurrency.value)?.data ?: mutableMapOf()
        }
    }

    fun setDefaultBaseCurrency(currency: String) {
        _currentBaseCurrency.value = currency
    }

    fun updateCurrencyRates() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAllLatestCurrencyRates(_currentBaseCurrency.value)
            if (response != null) {
                _currencyRates.value = response.data
            } else {
                _currencyRates.value = mutableMapOf()
            }
        }
    }
}