package com.ragnastormdev.japaneselearningapp.ui.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ragnastormdev.japaneselearningapp.ui.audio.AudioPlayer

@Composable
fun KanaQuizScreen(
    type: String,
    onBack: () -> Unit,
    viewModel: KanaQuizViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val audioPlayer = remember(context) {
        AudioPlayer(context)
    }

    DisposableEffect(audioPlayer) {
        onDispose {
            audioPlayer.stop()
        }
    }

    LaunchedEffect(type) {
        viewModel.startQuiz(type)
    }

    LaunchedEffect(uiState.currentIndex) {
        audioPlayer.stop()
    }

    when {
        uiState.isLoading -> {
            QuizLoadingContent()
        }

        uiState.isFinished -> {
            QuizResultContent(
                correctAnswerCount = uiState.correctAnswerCount,
                incorrectAnswerCount = uiState.incorrectAnswerCount,
                totalQuestions = uiState.totalQuestions,
                hasIncorrectQuestions = uiState.hasIncorrectQuestions,
                onReviewIncorrectQuestions = {
                    audioPlayer.stop()
                    viewModel.startIncorrectQuestionsQuiz()
                },
                onRestart = {
                    audioPlayer.stop()
                    viewModel.restartQuiz(type)
                },
                onBack = {
                    audioPlayer.stop()
                    onBack()
                }
            )
        }

        uiState.currentQuestion != null -> {
            val currentQuestion =
                uiState.currentQuestion ?: return

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Quiz ${
                        type
                            .lowercase()
                            .replaceFirstChar { character ->
                                character.uppercase()
                            }
                    }",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = "Question ${uiState.currentIndex + 1} / ${uiState.totalQuestions}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "${uiState.answeredQuestions} réponse(s) sur ${uiState.totalQuestions}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                LinearProgressIndicator(
                    progress = {
                        uiState.progress
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier.height(32.dp)
                )

                Text(
                    text = "Quel est le romaji de ce kana ?",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                Text(
                    text = currentQuestion.character,
                    fontSize = 96.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                OutlinedButton(
                    onClick = {
                        audioPlayer.play(
                            currentQuestion.romaji
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Écouter"
                    )
                }

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    uiState.answerChoices.forEach { answerChoice ->

                        val isSelected =
                            uiState.selectedAnswer == answerChoice

                        val isCorrectAnswer =
                            answerChoice == uiState.correctAnswer

                        val buttonText = when {
                            uiState.isAnswered &&
                                    isCorrectAnswer -> {
                                "$answerChoice ✓"
                            }

                            uiState.isAnswered &&
                                    isSelected -> {
                                "$answerChoice ✗"
                            }

                            else -> {
                                answerChoice
                            }
                        }

                        if (isSelected) {
                            Button(
                                onClick = {
                                    viewModel.selectAnswer(
                                        answerChoice
                                    )
                                },
                                enabled = !uiState.isAnswered,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = buttonText
                                )
                            }
                        } else {
                            OutlinedButton(
                                onClick = {
                                    viewModel.selectAnswer(
                                        answerChoice
                                    )
                                },
                                enabled = !uiState.isAnswered,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = buttonText
                                )
                            }
                        }
                    }
                }

                if (uiState.isAnswered) {
                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    Text(
                        text = if (
                            uiState.isSelectedAnswerCorrect
                        ) {
                            "Bonne réponse !"
                        } else {
                            "La bonne réponse était : ${uiState.correctAnswer}"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = if (
                            uiState.isSelectedAnswerCorrect
                        ) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.error
                        }
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    Button(
                        onClick = {
                            audioPlayer.stop()
                            viewModel.nextQuestion()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (
                                uiState.currentIndex + 1 >=
                                uiState.totalQuestions
                            ) {
                                "Voir le résultat"
                            } else {
                                "Question suivante"
                            }
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                OutlinedButton(
                    onClick = {
                        audioPlayer.stop()
                        onBack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Quitter le quiz"
                    )
                }
            }
        }

        else -> {
            QuizEmptyContent(
                onBack = {
                    audioPlayer.stop()
                    onBack()
                }
            )
        }
    }
}

@Composable
private fun QuizLoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun QuizResultContent(
    correctAnswerCount: Int,
    incorrectAnswerCount: Int,
    totalQuestions: Int,
    hasIncorrectQuestions: Boolean,
    onReviewIncorrectQuestions: () -> Unit,
    onRestart: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Quiz terminé",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Text(
            text = "$correctAnswerCount / $totalQuestions bonnes réponses",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "$incorrectAnswerCount erreur(s)",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        if (hasIncorrectQuestions) {
            Button(
                onClick = onReviewIncorrectQuestions,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Revoir mes erreurs"
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }

        OutlinedButton(
            onClick = onRestart,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Nouveau quiz"
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Retour"
            )
        }
    }
}

@Composable
private fun QuizEmptyContent(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Aucune question disponible",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        OutlinedButton(
            onClick = onBack
        ) {
            Text(
                text = "Retour"
            )
        }
    }
}