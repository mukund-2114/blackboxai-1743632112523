package com.example.cenphone.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

data class PhoneModel(
    val id: String,
    val name: String,
    val brand: String,
    val price: Double,
    val imageUrl: String,
    val description: String
)

private val samplePhoneModels = mapOf(
    "apple" to listOf(
        PhoneModel(
            "iphone_15_pro",
            "iPhone 15 Pro",
            "Apple",
            999.99,
            "https://images.pexels.com/photos/5741605/pexels-photo-5741605.jpeg",
            "The most powerful iPhone ever with A17 Pro chip"
        ),
        PhoneModel(
            "iphone_15",
            "iPhone 15",
            "Apple",
            799.99,
            "https://images.pexels.com/photos/5741605/pexels-photo-5741605.jpeg",
            "Amazing camera system with advanced features"
        )
    ),
    "samsung" to listOf(
        PhoneModel(
            "galaxy_s24_ultra",
            "Galaxy S24 Ultra",
            "Samsung",
            1199.99,
            "https://images.pexels.com/photos/404280/pexels-photo-404280.jpeg",
            "Ultimate Android experience with S Pen"
        ),
        PhoneModel(
            "galaxy_s24",
            "Galaxy S24",
            "Samsung",
            799.99,
            "https://images.pexels.com/photos/404280/pexels-photo-404280.jpeg",
            "Premium features in a compact design"
        )
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneModelScreen(
    onModelSelected: (String) -> Unit,
    onNavigateBack: () -> Unit,
    brandId: String = "apple",
    modifier: Modifier = Modifier
) {
    val phoneModels = samplePhoneModels[brandId] ?: emptyList()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Model") },
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
                    text = "Choose Your Phone Model",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Select from our latest collection",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            
            // Phone Models List
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(phoneModels) { model ->
                    PhoneModelCard(
                        model = model,
                        onModelClick = { onModelSelected(model.id) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneModelCard(
    model: PhoneModel,
    onModelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onModelClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Phone Image
            AsyncImage(
                model = model.imageUrl,
                contentDescription = model.name,
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Fit
            )
            
            // Phone Details
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = model.name,
                    style = MaterialTheme.typography.titleLarge
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "$${model.price}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = model.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            
            // Add to Cart Icon
            IconButton(
                onClick = onModelClick,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = "Add to Cart",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PhoneModelScreenPreview() {
    CenPhoneTheme {
        PhoneModelScreen(
            onModelSelected = {},
            onNavigateBack = {}
        )
    }
}