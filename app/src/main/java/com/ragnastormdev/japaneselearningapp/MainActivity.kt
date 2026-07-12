package com.ragnastormdev.japaneselearningapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ragnastormdev.japaneselearningapp.ui.navigation.AppNavigation
import com.ragnastormdev.japaneselearningapp.ui.theme.JapaneseLearningAppTheme
import com.ragnastormdev.japaneselearningapp.ui.viewmodel.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val themeMode by themeViewModel.themeMode.collectAsState()

            JapaneseLearningAppTheme(
                themeMode = themeMode
            ) {
                AppNavigation()
            }
        }
    }
}