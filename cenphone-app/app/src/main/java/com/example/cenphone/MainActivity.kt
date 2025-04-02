package com.example.cenphone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.cenphone.navigation.NavGraph
import com.example.cenphone.ui.theme.CenPhoneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CenPhoneApp()
        }
    }
}

@Composable
fun CenPhoneApp() {
    CenPhoneTheme {
        val navController = rememberNavController()
        NavGraph(navController = navController)
    }
}