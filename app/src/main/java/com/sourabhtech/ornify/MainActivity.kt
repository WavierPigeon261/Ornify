package com.sourabhtech.ornify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sourabhtech.ornify.ui.OrderViewModel
import com.sourabhtech.ornify.ui.screens.DashboardScreen
import com.sourabhtech.ornify.ui.screens.HistoryScreen
import com.sourabhtech.ornify.ui.screens.NewOrderScreen
import com.sourabhtech.ornify.ui.theme.OrnifyTheme

class MainActivity : ComponentActivity() {

    private val viewModel: OrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Material You dynamic theming enabled by default
            OrnifyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "dashboard"
                    ) {
                        composable("dashboard") {
                            DashboardScreen(
                                viewModel = viewModel,
                                onNavigateToNewOrder = {
                                    navController.navigate("new_order")
                                },
                                onNavigateToHistory = {
                                    navController.navigate("history")
                                },
                                onNavigateToEdit = {
                                    navController.navigate("new_order")
                                }
                            )
                        }
                        composable("new_order") {
                            NewOrderScreen(
                                viewModel = viewModel,
                                onNavigateBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable("history") {
                            HistoryScreen(
                                viewModel = viewModel,
                                onNavigateBack = {
                                    navController.popBackStack()
                                },
                                onNavigateToEdit = {
                                    navController.navigate("new_order")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
