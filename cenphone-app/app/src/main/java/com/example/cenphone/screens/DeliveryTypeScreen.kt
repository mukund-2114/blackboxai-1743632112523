package com.example.cenphone.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

data class DeliveryOption(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val estimatedDays: String,
    val icon: @Composable () -> Unit
)

private val deliveryOptions = listOf(
    DeliveryOption(
        "standard",
        "Standard Delivery",
        "Delivery within 5-7 business days",
        9.99,
        "5-7 days",
        { Icon(Icons.Default.LocalShipping, contentDescription = null) }
    ),
    DeliveryOption(
        "express",
        "Express Delivery",
        "Delivery within 2-3 business days",
        19.99,
        "2-3 days",
        { Icon(Icons.Default.Speed, contentDescription = null) }
    ),
    DeliveryOption(
        "same_day",
        "Same Day Delivery",
        "Delivery today (order before 2 PM)",
        29.99,
        "Today",
        { Icon(Icons.Default.Schedule, contentDescription = null) }
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryTypeScreen(
    onDeliverySelected: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedDeliveryOption by remember { mutableStateOf(deliveryOptions[0].id) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Choose Delivery") },
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
        ) {
            // Header Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Select Delivery Method",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Choose your preferred delivery option",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            // Delivery Options
            Column(
                modifier = Modifier
                    .selectableGroup()
                    .padding(16.dp)
                    .weight(1f)
            ) {
                deliveryOptions.forEach { option ->
                    DeliveryOptionCard(
                        option = option,
                        selected = selectedDeliveryOption == option.id,
                        onSelect = { selectedDeliveryOption = option.id }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Bottom Section with Total and Continue Button
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Order Summary
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
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
                            Text("$${deliveryOptions.find { it.id == selectedDeliveryOption }?.price}")
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
                                "$${String.format("%.2f", 999.99 + (deliveryOptions.find { it.id == selectedDeliveryOption }?.price ?: 0.0))}",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDeliverySelected,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("Continue to Payment")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryOptionCard(
    option: DeliveryOption,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                onClick = onSelect,
                role = Role.RadioButton
            ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (selected) 8.dp else 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selected,
                onClick = null
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            option.icon()
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = option.name,
                    style = MaterialTheme.typography.titleMedium
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = option.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Text(
                text = "$${option.price}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}