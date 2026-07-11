package com.ragnastormdev.japaneselearningapp.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ragnastormdev.japaneselearningapp.data.local.entity.KanaEntity
import com.ragnastormdev.japaneselearningapp.data.repository.KanaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class KanaDetailUiState(
    val kana: KanaEntity? = null,
    val previousKanaId: Int? = null,
    val nextKanaId: Int? = null
)

@HiltViewModel
class KanaDetailViewModel @Inject constructor(
    kanaRepository: KanaRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val initialKanaId =
        savedStateHandle.get<Int>("kanaId") ?: 1

    private val selectedKanaId =
        MutableStateFlow(initialKanaId)

    private val hiragana = kanaRepository
        .getKanaByType("HIRAGANA")
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val uiState = combine(
        hiragana,
        selectedKanaId
    ) { kanaList, kanaId ->

        val currentIndex = kanaList.indexOfFirst { kana ->
            kana.id == kanaId
        }

        KanaDetailUiState(
            kana = kanaList.getOrNull(currentIndex),
            previousKanaId = kanaList
                .getOrNull(currentIndex - 1)
                ?.id,
            nextKanaId = kanaList
                .getOrNull(currentIndex + 1)
                ?.id
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = KanaDetailUiState()
    )

    fun showPreviousKana() {
        uiState.value.previousKanaId?.let { previousId ->
            selectedKanaId.value = previousId
        }
    }

    fun showNextKana() {
        uiState.value.nextKanaId?.let { nextId ->
            selectedKanaId.value = nextId
        }
    }
}