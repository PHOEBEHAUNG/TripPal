package com.codingdrama.trippal.composes

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.codingdrama.trippal.viewnodel.RateViewModel

// RateScreen.kt
@Composable
fun RateScreen(modifier: Modifier = Modifier, context: Context = LocalContext.current, rateViewModel: RateViewModel) {
    Box (modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "Rate Screen",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}