package com.ragnastormdev.japaneselearningapp.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ragnastormdev.japaneselearningapp.data.local.entity.KanaEntity
import com.ragnastormdev.japaneselearningapp.data.repository.KanaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltViewModel
class KanaQuizViewModel @Inject constructor(
    private val kanaRepository: KanaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        KanaQuizState()
    )

    val uiState: StateFlow<KanaQuizState> =
        _uiState.asStateFlow()

    private var availableKana: List<KanaEntity> = emptyList()

    fun startQuiz(type: String) {
        _uiState.value = KanaQuizState(
            isLoading = true
        )

        viewModelScope.launch {
            availableKana = kanaRepository
                .getKanaByType(type.uppercase())
                .first()

            val quizQuestions = selectQuizQuestions(
                kana = availableKana
            )

            if (quizQuestions.isEmpty()) {
                _uiState.value = KanaQuizState(
                    isLoading = false
                )

                return@launch
            }

            _uiState.value = KanaQuizState(
                questions = quizQuestions,
                answerChoices = createAnswerChoices(
                    question = quizQuestions.first()
                ),
                isLoading = false
            )
        }
    }

    private fun selectQuizQuestions(
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

    private fun createAnswerChoices(
        question: KanaEntity
    ): List<String> {
        val incorrectAnswers = availableKana
            .asSequence()
            .filter { kana ->
                kana.id != question.id
            }
            .map { kana ->
                kana.romaji
            }
            .distinct()
            .shuffled()
            .take(3)
            .toList()

        return (
                incorrectAnswers +
                        question.romaji
                ).shuffled()
    }

    fun selectAnswer(answer: String) {
        val currentState = _uiState.value
        val currentQuestion =
            currentState.currentQuestion ?: return

        if (
            currentState.isLoading ||
            currentState.isFinished ||
            currentState.isAnswered
        ) {
            return
        }

        val isCorrect =
            answer == currentQuestion.romaji

        viewModelScope.launch {
            kanaRepository.updateKnownStatus(
                kanaId = currentQuestion.id,
                isKnown = isCorrect
            )

            val updatedIncorrectQuestions =
                if (isCorrect) {
                    currentState.incorrectQuestions
                } else {
                    currentState.incorrectQuestions +
                            currentQuestion
                }

            _uiState.value = currentState.copy(
                incorrectQuestions = updatedIncorrectQuestions,
                selectedAnswer = answer,
                correctAnswerCount = if (isCorrect) {
                    currentState.correctAnswerCount + 1
                } else {
                    currentState.correctAnswerCount
                },
                incorrectAnswerCount = if (isCorrect) {
                    currentState.incorrectAnswerCount
                } else {
                    currentState.incorrectAnswerCount + 1
                },
                isAnswered = true
            )
        }
    }

    fun nextQuestion() {
        val currentState = _uiState.value

        if (
            currentState.isLoading ||
            currentState.isFinished ||
            !currentState.isAnswered
        ) {
            return
        }

        val nextIndex =
            currentState.currentIndex + 1

        val quizFinished =
            nextIndex >= currentState.totalQuestions

        if (quizFinished) {
            _uiState.value = currentState.copy(
                isFinished = true
            )

            return
        }

        val nextQuestion =
            currentState.questions[nextIndex]

        _uiState.value = currentState.copy(
            currentIndex = nextIndex,
            answerChoices = createAnswerChoices(
                question = nextQuestion
            ),
            selectedAnswer = null,
            isAnswered = false
        )
    }

    fun startIncorrectQuestionsQuiz() {
        val questionsToReview =
            _uiState.value.incorrectQuestions
                .distinctBy { question ->
                    question.id
                }
                .shuffled()

        if (questionsToReview.isEmpty()) {
            return
        }

        _uiState.value = KanaQuizState(
            questions = questionsToReview,
            answerChoices = createAnswerChoices(
                question = questionsToReview.first()
            ),
            isLoading = false
        )
    }

    fun restartQuiz(type: String) {
        startQuiz(type)
    }
}