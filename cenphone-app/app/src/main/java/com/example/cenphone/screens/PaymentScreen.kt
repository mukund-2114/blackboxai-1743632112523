package com.example.cenphone.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    onPaymentComplete: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var cardNumber by remember { mutableStateOf("") }
    var cardHolder by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var isProcessing by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Payment") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Payment Information Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Payment Details",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Card Number Field
                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = { 
                        if (it.length <= 16) cardNumber = it.filter { char -> char.isDigit() }
                    },
                    label = { Text("Card Number") },
                    leadingIcon = {
                        Icon(Icons.Default.CreditCard, contentDescription = "Card")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("1234 5678 9012 3456") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Card Holder Field
                OutlinedTextField(
                    value = cardHolder,
                    onValueChange = { cardHolder = it },
                    label = { Text("Card Holder Name") },
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = "Person")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("JOHN DOE") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Expiry Date and CVV Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Expiry Date Field
                    OutlinedTextField(
                        value = expiryDate,
                        onValueChange = { 
                            if (it.length <= 5) {
                                expiryDate = it.filter { char -> char.isDigit() || char == '/' }
                                if (it.length == 2 && !it.contains("/")) {
                                    expiryDate = "$it/"
                                }
                            }
                        },
                        label = { Text("Expiry Date") },
                        leadingIcon = {
                            Icon(Icons.Default.DateRange, contentDescription = "Date")
                        },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        placeholder = { Text("MM/YY") }
                    )

                    // CVV Field
                    OutlinedTextField(
                        value = cvv,
                        onValueChange = { 
                            if (it.length <= 3) cvv = it.filter { char -> char.isDigit() }
                        },
                        label = { Text("CVV") },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = "CVV")
                        },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        placeholder = { Text("123") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Order Summary
                PaymentSummaryCard()

                Spacer(modifier = Modifier.height(24.dp))

                // Pay Button
                Button(
                    onClick = {
                        isProcessing = true
                        // Simulate payment processing
                        // In a real app, this would make an API call to process the payment
                        onPaymentComplete()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = !isProcessing && 
                             cardNumber.length == 16 && 
                             cardHolder.isNotBlank() && 
                             expiryDate.length == 5 && 
                             cvv.length == 3
                ) {
                    if (isProcessing) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Pay $1029.98")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Secure Payment Notice
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = "Secure",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Secure Payment",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

@Composable
fun PaymentSummaryCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Order Summary",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Subtotal")
                Text("$999.99")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Delivery")
                Text("$29.99")
            }

            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Total",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "$1029.98",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PaymentScreenPreview() {
    CenPhoneTheme {
        PaymentScreen(
            onPaymentComplete = {},
            onNavigateBack = {}
        )
    }
}