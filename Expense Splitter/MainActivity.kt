package com.example.expensesplitter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseSplitterApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseSplitterApp() {
    // State variables
    var billAmountInput by rememberSaveable { mutableStateOf("") }
    var numPeopleInput by rememberSaveable { mutableStateOf("") }
    var roundUp by rememberSaveable { mutableStateOf(false) }

    var perPersonAmount by rememberSaveable { mutableStateOf(0.0) }

    // Error states
    var billError by remember { mutableStateOf(false) }
    var peopleError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Expense Splitter", fontSize = 28.sp, modifier = Modifier.padding(bottom = 16.dp))

        // Bill Amount Input
        OutlinedTextField(
            value = billAmountInput,
            onValueChange = {
                billAmountInput = it
                billError = it.toDoubleOrNull() == null || it.toDoubleOrNull()!! <= 0
            },
            label = { Text("Bill Amount") },
            isError = billError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        if (billError) {
            Text(
                text = "Bill amount must be greater than 0",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Number of People Input
        OutlinedTextField(
            value = numPeopleInput,
            onValueChange = {
                numPeopleInput = it
                peopleError = it.toIntOrNull() == null || it.toIntOrNull()!! < 1
            },
            label = { Text("Number of People") },
            isError = peopleError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        if (peopleError) {
            Text(
                text = "Number of people must be at least 1",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Round Up Toggle
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Round Up", fontSize = 16.sp)
            Switch(
                checked = roundUp,
                onCheckedChange = { roundUp = it },
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Calculate Button
        Button(
            onClick = {
                val bill = billAmountInput.toDoubleOrNull() ?: 0.0
                val people = numPeopleInput.toIntOrNull() ?: 1
                var amount = if (people > 0) bill / people else 0.0
                if (roundUp) amount = kotlin.math.ceil(amount)
                perPersonAmount = amount
            },
            enabled = !billError && !peopleError,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Result Display
        if (perPersonAmount > 0.0) {
            Text(
                text = "Each person pays: $${"%.2f".format(perPersonAmount)}",
                fontSize = 20.sp,
                color = Color(0xFF00695C)
            )
        }
    }
}
