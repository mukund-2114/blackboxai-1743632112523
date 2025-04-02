package com.example.cenphone.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

data class Brand(
    val id: String,
    val name: String,
    val imageUrl: String
)

private val brands = listOf(
    Brand(
        "apple",
        "Apple",
        "https://images.pexels.com/photos/1294886/pexels-photo-1294886.jpeg"
    ),
    Brand(
        "samsung",
        "Samsung",
        "https://images.pexels.com/photos/214487/pexels-photo-214487.jpeg"
    ),
    Brand(
        "google",
        "Google",
        "https://images.pexels.com/photos/1482061/pexels-photo-1482061.jpeg"
    ),
    Brand(
        "xiaomi",
        "Xiaomi",
        "https://images.pexels.com/photos/404280/pexels-photo-404280.jpeg"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrandsScreen(
    onBrandSelected: (String) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Brand") },
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
                    text = "Choose Your Preferred Brand",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Select from our collection of premium phone brands",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            
            // Brands Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(brands) { brand ->
                    BrandCard(
                        brand = brand,
                        onBrandClick = { onBrandSelected(brand.id) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrandCard(
    brand: Brand,
    onBrandClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = onBrandClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = brand.imageUrl,
                contentDescription = "${brand.name} logo",
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .aspectRatio(1f)
                    .padding(8.dp),
                contentScale = ContentScale.Fit
            )
            
            Text(
                text = brand.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BrandsScreenPreview() {
    CenPhoneTheme {
        BrandsScreen(
            onBrandSelected = {},
            onNavigateBack = {}
        )
    }
}