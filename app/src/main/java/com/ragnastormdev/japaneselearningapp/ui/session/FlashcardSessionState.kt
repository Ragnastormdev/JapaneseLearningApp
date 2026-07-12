package com.ragnastormdev.japaneselearningapp.ui.session

import com.ragnastormdev.japaneselearningapp.data.local.entity.KanaEntity

data class FlashcardSessionState(
    val cards: List<KanaEntity> = emptyList(),
    val reviewCards: List<KanaEntity> = emptyList(),
    val currentIndex: Int = 0,
    val knownCount: Int = 0,
    val reviewCount: Int = 0,
    val isLoading: Boolean = true,
    val isFinished: Boolean = false
) {

    val currentCard: KanaEntity?
        get() = cards.getOrNull(currentIndex)

    val totalCards: Int
        get() = cards.size

    val answeredCards: Int
        get() = knownCount + reviewCount

    val hasReviewCards: Boolean
        get() = reviewCards.isNotEmpty()

    val progress: Float
        get() {
            if (totalCards == 0) {
                return 0f
            }

            return answeredCards.toFloat() / totalCards.toFloat()
        }
}

enum class SessionAnswer {
    KNOWN,
    REVIEW
}