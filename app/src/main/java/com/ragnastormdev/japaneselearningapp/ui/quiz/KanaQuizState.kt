package com.ragnastormdev.japaneselearningapp.ui.quiz

import com.ragnastormdev.japaneselearningapp.data.local.entity.KanaEntity

data class KanaQuizState(
    val questions: List<KanaEntity> = emptyList(),
    val incorrectQuestions: List<KanaEntity> = emptyList(),
    val currentIndex: Int = 0,
    val answerChoices: List<String> = emptyList(),
    val selectedAnswer: String? = null,
    val correctAnswerCount: Int = 0,
    val incorrectAnswerCount: Int = 0,
    val isLoading: Boolean = true,
    val isAnswered: Boolean = false,
    val isFinished: Boolean = false
) {

    val currentQuestion: KanaEntity?
        get() = questions.getOrNull(currentIndex)

    val correctAnswer: String
        get() = currentQuestion?.romaji.orEmpty()

    val totalQuestions: Int
        get() = questions.size

    val answeredQuestions: Int
        get() = correctAnswerCount + incorrectAnswerCount

    val hasIncorrectQuestions: Boolean
        get() = incorrectQuestions.isNotEmpty()

    val isSelectedAnswerCorrect: Boolean
        get() {
            return selectedAnswer != null &&
                    selectedAnswer == correctAnswer
        }

    val progress: Float
        get() {
            if (totalQuestions == 0) {
                return 0f
            }

            return answeredQuestions.toFloat() /
                    totalQuestions.toFloat()
        }
}