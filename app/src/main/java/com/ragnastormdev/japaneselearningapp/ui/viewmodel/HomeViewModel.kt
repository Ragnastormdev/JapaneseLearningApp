package com.ragnastormdev.japaneselearningapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ragnastormdev.japaneselearningapp.data.local.entity.KanaEntity
import com.ragnastormdev.japaneselearningapp.data.local.source.KanaJsonDataSource
import com.ragnastormdev.japaneselearningapp.data.repository.KanaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val kanaRepository: KanaRepository,
    private val kanaJsonDataSource: KanaJsonDataSource
) : ViewModel() {

    val title = "Accueil"

    private val currentTime =
        System.currentTimeMillis()

    val kanaCount = kanaRepository.observeCount()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0
        )

    val hiragana = kanaRepository.getKanaByType("HIRAGANA")
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList<KanaEntity>()
        )

    val katakana = kanaRepository.getKanaByType("KATAKANA")
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList<KanaEntity>()
        )

    val knownHiraganaCount =
        kanaRepository.observeKnownCountByType("HIRAGANA")
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = 0
            )

    val knownKatakanaCount =
        kanaRepository.observeKnownCountByType("KATAKANA")
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = 0
            )

    val totalKnownCount =
        kanaRepository.observeTotalKnownCount()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = 0
            )

    val dueHiraganaCount =
        kanaRepository.observeDueKanaCountByType(
            type = "HIRAGANA",
            currentTime = currentTime
        ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0
        )

    val dueKatakanaCount =
        kanaRepository.observeDueKanaCountByType(
            type = "KATAKANA",
            currentTime = currentTime
        ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0
        )

    init {
        initializeKanaDatabase()
    }

    private fun initializeKanaDatabase() {
        viewModelScope.launch {
            if (kanaRepository.getCount() == 0) {
                val kana =
                    kanaJsonDataSource.loadHiragana() +
                            kanaJsonDataSource.loadKatakana()

                kanaRepository.insertAll(kana)
            }
        }
    }
}