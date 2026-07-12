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

    var selectedAlphabet by rememberSaveable {
        mutableStateOf<SelectedAlphabet?>(null)
    }

    when (selectedAlphabet) {
        null -> {
            AlphabetSelectionScreen(
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
    onHiraganaClick: () -> Unit,
    onKatakanaClick: () -> Unit
) {
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
            modifier = Modifier.height(40.dp)
        )

        Button(
            onClick = onHiraganaClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Hiragana",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = onKatakanaClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Katakana",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
private fun KanaGridScreen(
    title: String,
    kanaList: List<KanaEntity>,
    onKanaClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val outlinedButtonColors = ButtonDefaults.outlinedButtonColors(
        contentColor = MaterialTheme.colorScheme.primary
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
                    bottom = 16.dp
                ),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
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
            containerColor = MaterialTheme.colorScheme.surfaceVariant
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
            }
        }
    }
}