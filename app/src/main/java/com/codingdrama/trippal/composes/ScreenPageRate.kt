package com.codingdrama.trippal.composes

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.codingdrama.trippal.viewnodel.RateViewModel

@Composable
fun RateScreen(modifier: Modifier = Modifier, context: Context = LocalContext.current, rateViewModel: RateViewModel) {
    Column(modifier = modifier.fillMaxSize()) {
        val currentBaseCurrency by rateViewModel.currentBaseCurrency.collectAsState()
        val supportedCurrenciesList by rateViewModel.supportedCurrenciesList.collectAsState()
        val supportedCurrencies by rateViewModel.supportedCurrencies.collectAsState()
        val currencyRates by rateViewModel.currencyRates.collectAsState()

        DropdownMenuCurrencies(
            modifier = Modifier.fillMaxWidth().align(Alignment.End),
            context = context,
            currentBaseCurrency,
            selections = supportedCurrenciesList ?: emptyList(),
        ) {
            rateViewModel.setDefaultBaseCurrency(it)
            rateViewModel.updateCurrencyRates()
        }

        // show currencies (Map<String, Float>) with card view
        Box(modifier = Modifier.fillMaxSize().padding(16.dp, 0.dp, 16.dp, 16.dp)) {
            LazyColumn {
                currencyRates.forEach { (currency, rate) ->
                    item {
                        CardCurrencyInfo(
                            modifier = Modifier.padding(8.dp),
                            currencyDetails = supportedCurrencies?.data?.get(currency)!!,
                            rate = rate
                        )
                    }
                }
            }
        }
    }
}