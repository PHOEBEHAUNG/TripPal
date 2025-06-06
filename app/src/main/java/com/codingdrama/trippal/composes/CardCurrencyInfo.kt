package com.codingdrama.trippal.composes

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codingdrama.trippal.model.network.data.CurrencyDetails

@Composable
fun CardCurrencyInfo(modifier: Modifier = Modifier, context: Context = LocalContext.current, currencyDetails: CurrencyDetails, rate: Float, onClick: (Float) -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .absolutePadding(20.dp, 10.dp, 20.dp, 10.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = { onClick(rate) },
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(
                text = "${currencyDetails.code}(${currencyDetails.symbol})",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "$rate",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewCardCurrencyInfo() {
    val fakeCurrencyDetails = CurrencyDetails(
        code = "USD",
        symbol = "$",
        name = "United States Dollar",
        symbolNative = "$",
        decimalDigits = 2,
        rounding = 0,
        namePlural = "US Dollars"
    )

    CardCurrencyInfo(
        currencyDetails = fakeCurrencyDetails,
        rate = 1.0f,
        onClick = {}
    )
}