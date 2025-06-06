package com.codingdrama.trippal.composes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun CurrencyRateCalculator(modifier: Modifier = Modifier, rate: Float, onDismiss: () -> Unit) {
    var inputValue by remember { mutableStateOf("") }
    val convertedValue = remember(inputValue, rate) {
        inputValue.toFloatOrNull()?.let { it * rate } ?: 0f
    }

    Surface(modifier = modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(20.dp)) {
        Column(
            modifier = modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CurrencyDisplaySection(
                inputValue = inputValue,
                convertedValue = convertedValue
            )

            val buttons = listOf(
                listOf("7", "8", "9"),
                listOf("4", "5", "6"),
                listOf("1", "2", "3"),
                listOf("0", ".", "C")
            )

            buttons.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { label ->
                        Button(
                            onClick = {
                                when (label) {
                                    "C" -> inputValue = ""
                                    "." -> if (!inputValue.contains(".")) inputValue += "."
                                    else -> inputValue += label
                                }
                            },
                            modifier = Modifier
                                .size(70.dp)
                        ) {
                            Text(text = label, fontSize = 20.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CurrencyDisplaySection(modifier: Modifier = Modifier, inputValue: String, convertedValue: Float) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val (convertedBox, arrowText, inputBox) = createRefs()

        Text(
            text = if(inputValue.isEmpty()) "0.0" else inputValue,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            modifier = Modifier
                .constrainAs(convertedBox) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(arrowText.start)
                    width = Dimension.fillToConstraints
                }
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .padding(12.dp)
        )

        Text(
            text = " -> ",
            fontSize = 20.sp,
            modifier = Modifier
                .constrainAs(arrowText) {
                    top.linkTo(convertedBox.top)
                    bottom.linkTo(convertedBox.bottom)
                    start.linkTo(convertedBox.end)
                    end.linkTo(inputBox.start)
                }
                .padding(horizontal = 8.dp)
        )

        Text(
            text = String.format("%.1f", convertedValue),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            modifier = Modifier
                .constrainAs(inputBox) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    start.linkTo(arrowText.end)
                    width = Dimension.fillToConstraints
                }
                .background(Color.Gray, RoundedCornerShape(8.dp))
                .padding(12.dp)
        )
    }
}

@Preview
@Composable
fun CurrencyRateCalculatorPreview() {
    CurrencyRateCalculator(rate = 30.5f, onDismiss = { } )
}
