package com.example.cenphone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Brands : Screen("brands")
    object PhoneModel : Screen("phone_model")
    object Cart : Screen("cart")
    object Checkout : Screen("checkout")
    object DeliveryType : Screen("delivery_type")
    object Payment : Screen("payment")
    object OrderConfirmation : Screen("order_confirmation")
    object Profile : Screen("profile")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onOrderNowClick = {
                    navController.navigate(Screen.Brands.route)
                }
            )
        }
        
        composable(Screen.Brands.route) {
            BrandsScreen(
                onBrandSelected = { brandId ->
                    navController.navigate("${Screen.PhoneModel.route}/$brandId")
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(
            route = "${Screen.PhoneModel.route}/{brandId}"
        ) {
            PhoneModelScreen(
                onModelSelected = { modelId ->
                    navController.navigate(Screen.Cart.route)
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Cart.route) {
            CartScreen(
                onCheckoutClick = {
                    navController.navigate(Screen.Checkout.route)
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Checkout.route) {
            CheckoutScreen(
                onDeliveryTypeClick = {
                    navController.navigate(Screen.DeliveryType.route)
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.DeliveryType.route) {
            DeliveryTypeScreen(
                onDeliverySelected = {
                    navController.navigate(Screen.Payment.route)
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Payment.route) {
            PaymentScreen(
                onPaymentComplete = {
                    navController.navigate(Screen.OrderConfirmation.route) {
                        popUpTo(Screen.Home.route)
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.OrderConfirmation.route) {
            OrderConfirmationScreen(
                onBackToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Profile.route) {
            ProfileScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}