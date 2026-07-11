package com.ragnastormdev.japaneselearningapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ragnastormdev.japaneselearningapp.ui.navigation.AppNavigation
import com.ragnastormdev.japaneselearningapp.ui.theme.JapaneseLearningAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            JapaneseLearningAppTheme {
                AppNavigation()
            }
        }
    }
}