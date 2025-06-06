package com.codingdrama.trippal.composes

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.codingdrama.trippal.R
import com.codingdrama.trippal.model.utils.TimeUtils
import com.codingdrama.trippal.viewnodel.RateViewModel

@Composable
fun RateScreen(modifier: Modifier = Modifier, context: Context = LocalContext.current, rateViewModel: RateViewModel) {
    var calculatorRate by remember { mutableStateOf(-1.0f) }
    val lastUpdateTime by rateViewModel.lastUpdateTime.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        val currentBaseCurrency by rateViewModel.currentBaseCurrency.collectAsState()
        val supportedCurrenciesList by rateViewModel.supportedCurrenciesList.collectAsState()
        val supportedCurrencies by rateViewModel.supportedCurrencies.collectAsState()
        val currencyRates by rateViewModel.currencyRates.collectAsState()

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = context.getString(R.string.title_last_updated) + TimeUtils.getTimeFullString(lastUpdateTime),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(20.dp, 10.dp, 20.dp, 0.dp)
            )
            DropdownMenuCurrencies(
                modifier = Modifier.fillMaxWidth(),
                context = context,
                currentBaseCurrency,
                selections = supportedCurrenciesList ?: emptyList(),
            ) {
                rateViewModel.setDefaultBaseCurrency(it)
                rateViewModel.updateCurrencyRates()
            }
        }

        if (currencyRates.isNotEmpty()) {
            // show currencies (Map<String, Float>) with card view
            Box(modifier = Modifier.fillMaxSize().padding(16.dp, 0.dp, 16.dp, 16.dp)) {
                LazyColumn {
                    currencyRates.forEach { (currency, rate) ->
                        item {
                            CardCurrencyInfo(
                                modifier = Modifier.padding(8.dp),
                                currencyDetails = supportedCurrencies?.data?.get(currency)!!,
                                rate = rate,
                                onClick = { selectedRate ->
                                    calculatorRate = selectedRate
                                }
                            )
                        }
                    }
                }
            }
        } else {
            NoRateInformationBox(modifier = Modifier.fillMaxSize().padding(16.dp))
        }

        if (calculatorRate != -1.0f) {
            Dialog(onDismissRequest = { calculatorRate = -1.0f }) {
                CurrencyRateCalculator(
                    modifier = modifier.wrapContentSize(),
                    rate = calculatorRate, // Placeholder rate, replace with actual selected rate
                    onDismiss = { calculatorRate = -1.0f }
                )
            }
        }
    }
}

@Composable
fun NoRateInformationBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = LocalContext.current.getString(R.string.title_no_rate_information_available),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}