package com.codingdrama.trippal.composes

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.codingdrama.trippal.R
import androidx.compose.material3.DropdownMenu as DropdownMenu1

@Composable
fun DropdownMenuCurrencies(modifier: Modifier, context: Context = LocalContext.current, currentSelection: String, selections: List<String>, onClick: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth().padding(10.dp, 5.dp, 10.dp, 5.dp), contentAlignment = Alignment.TopEnd) {
        Surface(modifier = Modifier.padding(0.dp, 0.dp, 10.dp, 0.dp), onClick = { expanded = !expanded }) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Absolute.Right) {
                Text(currentSelection)
                Icon(
                    painter = painterResource(id = if (expanded) R.drawable.icon_round_arrow_drop_up_24 else R.drawable.icon_round_arrow_drop_down_24),
                    contentDescription = "DropDown Icon"
                )
            }
        }

        val configuration = LocalConfiguration.current
        val screenWidthDp = configuration.screenWidthDp.dp

        DropdownMenu1(
            modifier = Modifier.width(150.dp).height(300.dp).padding(0.dp, 20.dp, 0.dp, 20.dp).align(Alignment.TopEnd),
            expanded = expanded,
            offset = DpOffset(x = screenWidthDp - 150.dp, y = 0.dp),
            shape = Shapes().medium,
            onDismissRequest = { expanded = false }
        ) {
            selections.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        expanded = false
                        onClick(option)
                    }
                )
            }
        }
    }
}