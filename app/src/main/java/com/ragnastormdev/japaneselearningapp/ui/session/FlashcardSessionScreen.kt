package com.ragnastormdev.japaneselearningapp.ui.session

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ragnastormdev.japaneselearningapp.ui.audio.AudioPlayer
import com.ragnastormdev.japaneselearningapp.ui.components.PrimaryButton
import com.ragnastormdev.japaneselearningapp.ui.components.SecondaryButton

@Composable
fun FlashcardSessionScreen(
    type: String,
    isDueReviewSession: Boolean = false,
    onBack: () -> Unit,
    viewModel: FlashcardSessionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val audioPlayer = remember(context) {
        AudioPlayer(context)
    }

    var showAnswer by remember {
        mutableStateOf(false)
    }

    DisposableEffect(audioPlayer) {
        onDispose {
            audioPlayer.stop()
        }
    }

    LaunchedEffect(
        type,
        isDueReviewSession
    ) {
        if (isDueReviewSession) {
            viewModel.startDueReviewSession(type)
        } else {
            viewModel.startSession(type)
        }
    }

    LaunchedEffect(uiState.currentIndex) {
        showAnswer = false
        audioPlayer.stop()
    }

    when {
        uiState.isLoading -> {
            SessionLoadingContent()
        }

        uiState.isFinished -> {
            SessionResultContent(
                knownCount = uiState.knownCount,
                reviewCount = uiState.reviewCount,
                totalCards = uiState.totalCards,
                hasReviewCards = uiState.hasReviewCards,
                isDueReviewSession = isDueReviewSession,
                onReviewCards = {
                    showAnswer = false
                    audioPlayer.stop()
                    viewModel.startReviewSession()
                },
                onRestart = {
                    showAnswer = false
                    audioPlayer.stop()

                    if (isDueReviewSession) {
                        viewModel.startDueReviewSession(type)
                    } else {
                        viewModel.restartSession(type)
                    }
                },
                onBack = {
                    audioPlayer.stop()
                    onBack()
                }
            )
        }

        uiState.currentCard != null -> {
            val currentCard = uiState.currentCard ?: return

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(
                        start = 20.dp,
                        top = 24.dp,
                        end = 20.dp,
                        bottom = 20.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SessionHeader(
                    type = type,
                    isDueReviewSession = isDueReviewSession,
                    currentIndex = uiState.currentIndex,
                    totalCards = uiState.totalCards,
                    answeredCards = uiState.answeredCards,
                    progress = uiState.progress
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                FlashcardContent(
                    character = currentCard.character,
                    romaji = currentCard.romaji,
                    showAnswer = showAnswer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                PrimaryButton(
                    onClick = {
                        showAnswer = !showAnswer
                    }
                ) {
                    Text(
                        text = if (showAnswer) {
                            "Masquer la réponse"
                        } else {
                            "Afficher la réponse"
                        },
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                SecondaryButton(
                    onClick = {
                        audioPlayer.play(currentCard.romaji)
                    }
                ) {
                    Text(
                        text = "Écouter la prononciation",
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SecondaryButton(
                        onClick = {
                            audioPlayer.stop()
                            viewModel.answer(SessionAnswer.REVIEW)
                        },
                        enabled = showAnswer,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "À revoir",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    PrimaryButton(
                        onClick = {
                            audioPlayer.stop()
                            viewModel.answer(SessionAnswer.KNOWN)
                        },
                        enabled = showAnswer,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Je connais",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = if (showAnswer) {
                        "Choisis ton niveau de mémorisation."
                    } else {
                        "Affiche la réponse avant de choisir."
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                SecondaryButton(
                    onClick = {
                        audioPlayer.stop()
                        onBack()
                    }
                ) {
                    Text(
                        text = "Quitter la session"
                    )
                }
            }
        }

        else -> {
            SessionEmptyContent(
                isDueReviewSession = isDueReviewSession,
                onBack = {
                    audioPlayer.stop()
                    onBack()
                }
            )
        }
    }
}

@Composable
private fun SessionHeader(
    type: String,
    isDueReviewSession: Boolean,
    currentIndex: Int,
    totalCards: Int,
    answeredCards: Int,
    progress: Float
) {
    val sessionTitle = if (isDueReviewSession) {
        "Révisions du jour"
    } else {
        val formattedType = type
            .lowercase()
            .replaceFirstChar { character ->
                character.uppercase()
            }

        "Session $formattedType"
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = sessionTitle,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "$answeredCards réponse(s) enregistrée(s)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Text(
                    text = "${currentIndex + 1} / $totalCards",
                    modifier = Modifier.padding(
                        horizontal = 14.dp,
                        vertical = 9.dp
                    ),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        LinearProgressIndicator(
            progress = {
                progress
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(9.dp)
                .clip(RoundedCornerShape(9.dp))
        )
    }
}

@Composable
private fun FlashcardContent(
    character: String,
    romaji: String,
    showAnswer: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = character,
                    fontSize = 112.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(
                    modifier = Modifier.height(28.dp)
                )

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (showAnswer) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    }
                ) {
                    Text(
                        text = if (showAnswer) {
                            romaji
                        } else {
                            "?"
                        },
                        modifier = Modifier.padding(
                            horizontal = 28.dp,
                            vertical = 12.dp
                        ),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = if (showAnswer) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                Text(
                    text = if (showAnswer) {
                        "Prononciation révélée"
                    } else {
                        "Essaie de retrouver la prononciation"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun SessionLoadingContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "Préparation de la session…",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SessionResultContent(
    knownCount: Int,
    reviewCount: Int,
    totalCards: Int,
    hasReviewCards: Boolean,
    isDueReviewSession: Boolean,
    onReviewCards: () -> Unit,
    onRestart: () -> Unit,
    onBack: () -> Unit
) {
    val successRate = if (totalCards == 0) {
        0
    } else {
        (
                knownCount.toFloat() /
                        totalCards.toFloat() *
                        100
                ).toInt()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier.size(72.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✓",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = if (isDueReviewSession) {
                "Révisions terminées"
            } else {
                "Session terminée"
            },
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Voici le résultat de cette session.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$successRate %",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Taux de réussite",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ResultValue(
                        value = knownCount,
                        label = "Connus"
                    )

                    ResultValue(
                        value = reviewCount,
                        label = "À revoir"
                    )

                    ResultValue(
                        value = totalCards,
                        label = "Total"
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        if (hasReviewCards) {
            PrimaryButton(
                onClick = onReviewCards
            ) {
                Text(
                    text = "Revoir les cartes difficiles",
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )
        }

        SecondaryButton(
            onClick = onRestart
        ) {
            Text(
                text = if (isDueReviewSession) {
                    "Actualiser les révisions"
                } else {
                    "Nouvelle session"
                }
            )
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        SecondaryButton(
            onClick = onBack
        ) {
            Text(
                text = "Retour à l'accueil"
            )
        }
    }
}

@Composable
private fun ResultValue(
    value: Int,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(3.dp)
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun SessionEmptyContent(
    isDueReviewSession: Boolean,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier.size(72.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "—",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = if (isDueReviewSession) {
                "Aucune révision disponible aujourd'hui"
            } else {
                "Aucune carte disponible"
            },
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        if (isDueReviewSession) {
            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Les kana reviendront automatiquement lorsque leur date de révision sera atteinte.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        SecondaryButton(
            onClick = onBack
        ) {
            Text(
                text = "Retour à l'accueil"
            )
        }
    }
}