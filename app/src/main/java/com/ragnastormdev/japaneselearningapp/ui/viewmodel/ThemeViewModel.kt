package com.ragnastormdev.japaneselearningapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ragnastormdev.japaneselearningapp.data.preferences.ThemeMode
import com.ragnastormdev.japaneselearningapp.data.preferences.ThemePreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themePreference: ThemePreference
) : ViewModel() {

    val themeMode: StateFlow<ThemeMode> =
        themePreference.themeMode.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ThemeMode.SYSTEM
        )

    fun setThemeMode(themeMode: ThemeMode) {
        viewModelScope.launch {
            themePreference.saveThemeMode(themeMode)
        }
    }
}