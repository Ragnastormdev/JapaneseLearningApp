package com.ragnastormdev.japaneselearningapp.ui.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ragnastormdev.japaneselearningapp.data.local.entity.KanaEntity
import com.ragnastormdev.japaneselearningapp.data.repository.KanaRepository
import com.ragnastormdev.japaneselearningapp.domain.srs.SrsScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltViewModel
class FlashcardSessionViewModel @Inject constructor(
    private val kanaRepository: KanaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        FlashcardSessionState()
    )

    val uiState: StateFlow<FlashcardSessionState> =
        _uiState.asStateFlow()

    fun startSession(type: String) {
        _uiState.value = FlashcardSessionState(
            isLoading = true
        )

        viewModelScope.launch {
            val kana = kanaRepository
                .getKanaByType(type.uppercase())
                .first()

            val sessionCards = selectSessionCards(
                kana = kana
            )

            _uiState.value = FlashcardSessionState(
                cards = sessionCards,
                isLoading = false
            )
        }
    }

    fun startDueReviewSession(type: String) {
        _uiState.value = FlashcardSessionState(
            isLoading = true
        )

        viewModelScope.launch {
            val dueKana = kanaRepository
                .getDueKanaByType(
                    type = type.uppercase(),
                    currentTime = System.currentTimeMillis()
                )
                .first()

            val reviewCards = dueKana
                .take(10)

            _uiState.value = FlashcardSessionState(
                cards = reviewCards,
                isLoading = false
            )
        }
    }

    private fun selectSessionCards(
        kana: List<KanaEntity>
    ): List<KanaEntity> {
        val unknownKana = kana
            .filter { currentKana ->
                !currentKana.isKnown
            }
            .shuffled()

        val knownKana = kana
            .filter { currentKana ->
                currentKana.isKnown
            }
            .shuffled()

        val selectedUnknownKana = unknownKana.take(10)

        val remainingPlaces =
            10 - selectedUnknownKana.size

        val selectedKnownKana = if (remainingPlaces > 0) {
            knownKana.take(remainingPlaces)
        } else {
            emptyList()
        }

        return (
                selectedUnknownKana +
                        selectedKnownKana
                ).shuffled()
    }

    fun answer(answer: SessionAnswer) {
        val currentState = _uiState.value
        val currentCard = currentState.currentCard ?: return

        if (
            currentState.isLoading ||
            currentState.isFinished
        ) {
            return
        }

        viewModelScope.launch {
            val reviewResult = when (answer) {
                SessionAnswer.KNOWN -> {
                    SrsScheduler.calculateSuccess(
                        kana = currentCard
                    )
                }

                SessionAnswer.REVIEW -> {
                    SrsScheduler.calculateFailure()
                }
            }

            kanaRepository.updateReviewProgress(
                kanaId = currentCard.id,
                isKnown = reviewResult.isKnown,
                nextReviewAt = reviewResult.nextReviewAt,
                successfulReviewCount =
                    reviewResult.successfulReviewCount,
                reviewIntervalDays =
                    reviewResult.reviewIntervalDays
            )

            moveToNextCard(
                answer = answer,
                answeredCard = currentCard
            )
        }
    }

    private fun moveToNextCard(
        answer: SessionAnswer,
        answeredCard: KanaEntity
    ) {
        val currentState = _uiState.value

        val newKnownCount =
            if (answer == SessionAnswer.KNOWN) {
                currentState.knownCount + 1
            } else {
                currentState.knownCount
            }

        val newReviewCount =
            if (answer == SessionAnswer.REVIEW) {
                currentState.reviewCount + 1
            } else {
                currentState.reviewCount
            }

        val newReviewCards =
            if (answer == SessionAnswer.REVIEW) {
                currentState.reviewCards + answeredCard
            } else {
                currentState.reviewCards
            }

        val nextIndex =
            currentState.currentIndex + 1

        val sessionFinished =
            nextIndex >= currentState.totalCards

        _uiState.value = currentState.copy(
            currentIndex = if (sessionFinished) {
                currentState.currentIndex
            } else {
                nextIndex
            },
            reviewCards = newReviewCards,
            knownCount = newKnownCount,
            reviewCount = newReviewCount,
            isFinished = sessionFinished
        )
    }

    fun startReviewSession() {
        val cardsToReview =
            _uiState.value.reviewCards

        if (cardsToReview.isEmpty()) {
            return
        }

        _uiState.value = FlashcardSessionState(
            cards = cardsToReview.shuffled(),
            isLoading = false
        )
    }

    fun restartSession(type: String) {
        startSession(type)
    }
}