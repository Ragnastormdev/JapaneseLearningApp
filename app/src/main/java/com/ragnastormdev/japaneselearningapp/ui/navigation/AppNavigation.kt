package com.ragnastormdev.japaneselearningapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ragnastormdev.japaneselearningapp.ui.quiz.KanaQuizScreen
import com.ragnastormdev.japaneselearningapp.ui.session.FlashcardSessionScreen
import com.ragnastormdev.japaneselearningapp.ui.settings.AppearanceScreen
import com.ragnastormdev.japaneselearningapp.ui.statistics.StatisticsScreen
import com.ragnastormdev.japaneselearningapp.ui.viewmodel.HomeViewModel

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
                },
                onStartSession = { type ->
                    navController.navigate(
                        route = "flashcard_session/$type"
                    )
                },
                onStartQuiz = { type ->
                    navController.navigate(
                        route = "kana_quiz/$type"
                    )
                },
                onStartDueReviewSession = { type ->
                    navController.navigate(
                        route = "due_review_session/$type"
                    )
                },
                onOpenStatistics = {
                    navController.navigate(
                        route = "statistics"
                    )
                },
                onOpenAppearance = {
                    navController.navigate(
                        route = "appearance"
                    )
                }
            )
        }

        composable(
            route = "statistics"
        ) {
            val viewModel: HomeViewModel = hiltViewModel()

            val hiragana by viewModel.hiragana.collectAsState()
            val katakana by viewModel.katakana.collectAsState()

            val knownHiraganaCount by
            viewModel.knownHiraganaCount.collectAsState()

            val knownKatakanaCount by
            viewModel.knownKatakanaCount.collectAsState()

            val totalKnownCount by
            viewModel.totalKnownCount.collectAsState()

            val dueHiraganaCount by
            viewModel.dueHiraganaCount.collectAsState()

            val dueKatakanaCount by
            viewModel.dueKatakanaCount.collectAsState()

            StatisticsScreen(
                totalKnownCount = totalKnownCount,
                totalKanaCount = hiragana.size + katakana.size,
                knownHiraganaCount = knownHiraganaCount,
                totalHiraganaCount = hiragana.size,
                knownKatakanaCount = knownKatakanaCount,
                totalKatakanaCount = katakana.size,
                dueHiraganaCount = dueHiraganaCount,
                dueKatakanaCount = dueKatakanaCount,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "appearance"
        ) {
            AppearanceScreen(
                onBackClick = {
                    navController.popBackStack()
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

        composable(
            route = "flashcard_session/{type}",
            arguments = listOf(
                navArgument(
                    name = "type"
                ) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val type = backStackEntry.arguments
                ?.getString("type")
                .orEmpty()

            FlashcardSessionScreen(
                type = type,
                isDueReviewSession = false,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "due_review_session/{type}",
            arguments = listOf(
                navArgument(
                    name = "type"
                ) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val type = backStackEntry.arguments
                ?.getString("type")
                .orEmpty()

            FlashcardSessionScreen(
                type = type,
                isDueReviewSession = true,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "kana_quiz/{type}",
            arguments = listOf(
                navArgument(
                    name = "type"
                ) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val type = backStackEntry.arguments
                ?.getString("type")
                .orEmpty()

            KanaQuizScreen(
                type = type,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}