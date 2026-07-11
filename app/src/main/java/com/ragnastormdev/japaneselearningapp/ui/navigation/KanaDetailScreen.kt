package com.ragnastormdev.japaneselearningapp.ui.navigation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ragnastormdev.japaneselearningapp.ui.audio.AudioPlayer
import com.ragnastormdev.japaneselearningapp.ui.viewmodel.KanaDetailViewModel

@Composable
fun KanaDetailScreen(
    onBackClick: () -> Unit,
    viewModel: KanaDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var isAnswerVisible by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    val audioPlayer = remember {
        AudioPlayer(context)
    }

    DisposableEffect(audioPlayer) {
        onDispose {
            audioPlayer.stop()
        }
    }

    val kana = uiState.kana

    if (kana == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        return
    }

    LaunchedEffect(kana.id) {
        isAnswerVisible = false
    }

    val outlinedButtonColors = ButtonDefaults.outlinedButtonColors(
        contentColor = MaterialTheme.colorScheme.primary,
        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(
            alpha = 0.38f
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Flashcard",
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 24.dp,
                    bottom = 24.dp
                ),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Crossfade(
            targetState = kana,
            label = "kanaTransition"
        ) { displayedKana ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                    .clickable {
                        isAnswerVisible = !isAnswerVisible
                    },
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = displayedKana.character,
                            style = MaterialTheme.typography.displayLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(32.dp)
                        )

                        if (isAnswerVisible) {
                            Text(
                                text = displayedKana.romaji,
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Medium
                            )
                        } else {
                            Text(
                                text = "Appuie sur la carte pour voir la réponse",
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = {
                    viewModel.showPreviousKana()
                },
                enabled = uiState.previousKanaId != null,
                colors = outlinedButtonColors,
                modifier = Modifier.fillMaxWidth(0.48f)
            ) {
                Text(
                    text = "Précédent"
                )
            }

            OutlinedButton(
                onClick = {
                    viewModel.showNextKana()
                },
                enabled = uiState.nextKanaId != null,
                colors = outlinedButtonColors,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Suivant"
                )
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = {
                isAnswerVisible = !isAnswerVisible
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (isAnswerVisible) {
                    "Masquer la réponse"
                } else {
                    "Afficher la réponse"
                }
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedButton(
            onClick = {
                audioPlayer.play(kana.romaji)
            },
            colors = outlinedButtonColors,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "▶ Écouter"
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        OutlinedButton(
            onClick = onBackClick,
            colors = outlinedButtonColors,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Retour à la liste"
            )
        }
    }
}