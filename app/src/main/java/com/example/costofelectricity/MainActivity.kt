package com.example.costofelectricity

import android.R.attr.text
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.costofelectricity.ui.theme.CostOfElectricityTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CostOfElectricityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CostOfElectricityScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CostOfElectricityScreen(modifier: Modifier = Modifier){
    var kwh by remember { mutableStateOf("") }
    var kwhPrice by remember {
        mutableFloatStateOf(0.25f)
    }
    var reducedVat by remember {
        mutableStateOf(false)
    }
    val vat = if (reducedVat) 10f else 24f
    val consumption = kwh.toFloatOrNull() ?: 0f
    val electricityCost = consumption * kwhPrice
    val vatAmount = electricityCost * vat / 100
    val totalCost = electricityCost + vatAmount

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(8.dp)
    ){
        Text(
            text = "Cost of Electricity",
            color = MaterialTheme.colorScheme.primary,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = kwh,
            onValueChange = {kwh = it},
            label = {Text(text = "Consumption in kwh")},
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(text = "Price/kWh: %.2f €".format(kwhPrice))
        Slider(
            value = kwhPrice,
            onValueChange = { newValue ->
                kwhPrice = newValue
            },
            valueRange = 0.00f..1.20f,
            steps = 119,

        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = reducedVat,
                onCheckedChange = {
                    reducedVat = it
                }
            )

            Text(text = "VAT 10%")
        }

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Text(
                text = "Cost is: %.2f €".format(totalCost),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun CostOfElectricityScreenPreview() {
    CostOfElectricityTheme {
        CostOfElectricityScreen()
    }
}