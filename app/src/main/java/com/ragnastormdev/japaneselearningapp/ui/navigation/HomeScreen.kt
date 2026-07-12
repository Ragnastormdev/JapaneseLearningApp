package com.ragnastormdev.japaneselearningapp.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ragnastormdev.japaneselearningapp.data.local.entity.KanaEntity
import com.ragnastormdev.japaneselearningapp.ui.viewmodel.HomeViewModel

private enum class SelectedAlphabet {
    HIRAGANA,
    KATAKANA
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onKanaClick: (Int) -> Unit = {}
) {
    val hiragana by viewModel.hiragana.collectAsState()
    val katakana by viewModel.katakana.collectAsState()

    val knownHiraganaCount by viewModel.knownHiraganaCount.collectAsState()
    val knownKatakanaCount by viewModel.knownKatakanaCount.collectAsState()
    val totalKnownCount by viewModel.totalKnownCount.collectAsState()

    var selectedAlphabet by rememberSaveable {
        mutableStateOf<SelectedAlphabet?>(null)
    }

    when (selectedAlphabet) {
        null -> {
            AlphabetSelectionScreen(
                hiraganaKnownCount = knownHiraganaCount,
                hiraganaTotalCount = hiragana.size,
                katakanaKnownCount = knownKatakanaCount,
                katakanaTotalCount = katakana.size,
                totalKnownCount = totalKnownCount,
                totalKanaCount = hiragana.size + katakana.size,
                onHiraganaClick = {
                    selectedAlphabet = SelectedAlphabet.HIRAGANA
                },
                onKatakanaClick = {
                    selectedAlphabet = SelectedAlphabet.KATAKANA
                }
            )
        }

        SelectedAlphabet.HIRAGANA -> {
            KanaGridScreen(
                title = "Hiragana",
                kanaList = hiragana,
                knownCount = knownHiraganaCount,
                onKanaClick = onKanaClick,
                onBackClick = {
                    selectedAlphabet = null
                }
            )
        }

        SelectedAlphabet.KATAKANA -> {
            KanaGridScreen(
                title = "Katakana",
                kanaList = katakana,
                knownCount = knownKatakanaCount,
                onKanaClick = onKanaClick,
                onBackClick = {
                    selectedAlphabet = null
                }
            )
        }
    }
}

@Composable
private fun AlphabetSelectionScreen(
    hiraganaKnownCount: Int,
    hiraganaTotalCount: Int,
    katakanaKnownCount: Int,
    katakanaTotalCount: Int,
    totalKnownCount: Int,
    totalKanaCount: Int,
    onHiraganaClick: () -> Unit,
    onKatakanaClick: () -> Unit
) {
    val totalProgress = calculateProgress(
        knownCount = totalKnownCount,
        totalCount = totalKanaCount
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Japanese Learning",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Choisis un alphabet",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        ProgressSection(
            label = "Progression totale",
            knownCount = totalKnownCount,
            totalCount = totalKanaCount,
            progress = totalProgress
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Button(
            onClick = onHiraganaClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Hiragana",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "$hiraganaKnownCount / $hiraganaTotalCount connus",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = onKatakanaClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Katakana",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "$katakanaKnownCount / $katakanaTotalCount connus",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun KanaGridScreen(
    title: String,
    kanaList: List<KanaEntity>,
    knownCount: Int,
    onKanaClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val outlinedButtonColors = ButtonDefaults.outlinedButtonColors(
        contentColor = MaterialTheme.colorScheme.primary
    )

    val progress = calculateProgress(
        knownCount = knownCount,
        totalCount = kanaList.size
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 32.dp,
                    bottom = 12.dp
                ),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        ProgressSection(
            label = "Progression",
            knownCount = knownCount,
            totalCount = kanaList.size,
            progress = progress,
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
        )

        OutlinedButton(
            onClick = onBackClick,
            colors = outlinedButtonColors,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
        ) {
            Text(
                text = "Changer d'alphabet"
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                items = kanaList,
                key = { kana ->
                    kana.id
                }
            ) { kana ->
                KanaCard(
                    kana = kana,
                    onClick = {
                        onKanaClick(kana.id)
                    }
                )
            }
        }
    }
}

@Composable
private fun ProgressSection(
    label: String,
    knownCount: Int,
    totalCount: Int,
    progress: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "$label : $knownCount / $totalCount",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        LinearProgressIndicator(
            progress = {
                progress
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
    }
}

@Composable
private fun KanaCard(
    kana: KanaEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .aspectRatio(0.85f)
            .clickable {
                onClick()
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (kana.isKnown) {
                MaterialTheme.colorScheme.secondaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = kana.character,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = kana.romaji,
                    style = MaterialTheme.typography.bodySmall
                )

                if (kana.isKnown) {
                    Text(
                        text = "✓",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

private fun calculateProgress(
    knownCount: Int,
    totalCount: Int
): Float {
    if (totalCount == 0) {
        return 0f
    }

    return knownCount.toFloat() / totalCount.toFloat()
}