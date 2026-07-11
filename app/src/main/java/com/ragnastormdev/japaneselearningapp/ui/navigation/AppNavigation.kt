package com.ragnastormdev.japaneselearningapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable(
            route = "home"
        ) {
            HomeScreen(
                onKanaClick = { kanaId ->
                    navController.navigate(
                        route = "kana_detail/$kanaId"
                    )
                }
            )
        }

        composable(
            route = "kana_detail/{kanaId}",
            arguments = listOf(
                navArgument(
                    name = "kanaId"
                ) {
                    type = NavType.IntType
                }
            )
        ) {
            KanaDetailScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}