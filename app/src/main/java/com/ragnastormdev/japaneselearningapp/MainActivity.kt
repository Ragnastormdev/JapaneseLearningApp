package com.ragnastormdev.japaneselearningapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ragnastormdev.japaneselearningapp.ui.navigation.AppNavigation
import com.ragnastormdev.japaneselearningapp.ui.theme.JapaneseLearningAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JapaneseLearningAppTheme {
                AppNavigation()
            }
        }
    }
}