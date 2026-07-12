package com.ragnastormdev.japaneselearningapp.ui.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ragnastormdev.japaneselearningapp.data.local.entity.KanaEntity
import com.ragnastormdev.japaneselearningapp.ui.components.AppScaffold
import com.ragnastormdev.japaneselearningapp.ui.components.KanaCard
import com.ragnastormdev.japaneselearningapp.ui.components.PrimaryButton
import com.ragnastormdev.japaneselearningapp.ui.components.ProgressCard
import com.ragnastormdev.japaneselearningapp.ui.components.SecondaryButton
import com.ragnastormdev.japaneselearningapp.ui.viewmodel.HomeViewModel

private enum class SelectedAlphabet {
    HIRAGANA,
    KATAKANA
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onKanaClick: (Int) -> Unit = {},
    onStartSession: (String) -> Unit = {},
    onStartQuiz: (String) -> Unit = {},
    onStartDueReviewSession: (String) -> Unit = {},
    onOpenStatistics: () -> Unit = {},
    onOpenAppearance: () -> Unit = {}
) {
    val hiragana by viewModel.hiragana.collectAsState()
    val katakana by viewModel.katakana.collectAsState()

    val knownHiraganaCount by viewModel.knownHiraganaCount.collectAsState()
    val knownKatakanaCount by viewModel.knownKatakanaCount.collectAsState()
    val totalKnownCount by viewModel.totalKnownCount.collectAsState()

    val dueHiraganaCount by viewModel.dueHiraganaCount.collectAsState()
    val dueKatakanaCount by viewModel.dueKatakanaCount.collectAsState()

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
                dueHiraganaCount = dueHiraganaCount,
                dueKatakanaCount = dueKatakanaCount,
                onHiraganaClick = {
                    selectedAlphabet = SelectedAlphabet.HIRAGANA
                },
                onKatakanaClick = {
                    selectedAlphabet = SelectedAlphabet.KATAKANA
                },
                onStartDueReviewSession = onStartDueReviewSession,
                onOpenStatistics = onOpenStatistics,
                onOpenAppearance = onOpenAppearance
            )
        }

        SelectedAlphabet.HIRAGANA -> {
            KanaGridScreen(
                title = "Hiragana",
                japaneseTitle = "ひらがな",
                kanaType = "HIRAGANA",
                kanaList = hiragana,
                knownCount = knownHiraganaCount,
                dueReviewCount = dueHiraganaCount,
                onKanaClick = onKanaClick,
                onStartSession = onStartSession,
                onStartQuiz = onStartQuiz,
                onStartDueReviewSession = onStartDueReviewSession,
                onBackClick = {
                    selectedAlphabet = null
                }
            )
        }

        SelectedAlphabet.KATAKANA -> {
            KanaGridScreen(
                title = "Katakana",
                japaneseTitle = "カタカナ",
                kanaType = "KATAKANA",
                kanaList = katakana,
                knownCount = knownKatakanaCount,
                dueReviewCount = dueKatakanaCount,
                onKanaClick = onKanaClick,
                onStartSession = onStartSession,
                onStartQuiz = onStartQuiz,
                onStartDueReviewSession = onStartDueReviewSession,
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
    dueHiraganaCount: Int,
    dueKatakanaCount: Int,
    onHiraganaClick: () -> Unit,
    onKatakanaClick: () -> Unit,
    onStartDueReviewSession: (String) -> Unit,
    onOpenStatistics: () -> Unit,
    onOpenAppearance: () -> Unit
) {
    val totalDueCount = dueHiraganaCount + dueKatakanaCount

    AppScaffold(
        title = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.size(64.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "日",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Text(
                text = "Japanese Learning",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Progresse un kana à la fois",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            ProgressCard(
                title = "Progression totale",
                knownCount = totalKnownCount,
                totalCount = totalKanaCount
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            DailyReviewCard(
                dueCount = totalDueCount,
                onClick = {
                    when {
                        dueHiraganaCount > 0 -> {
                            onStartDueReviewSession("HIRAGANA")
                        }

                        dueKatakanaCount > 0 -> {
                            onStartDueReviewSession("KATAKANA")
                        }
                    }
                }
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            StatisticsEntryCard(
                onClick = onOpenStatistics
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            AppearanceEntryCard(
                onClick = onOpenAppearance
            )

            Spacer(
                modifier = Modifier.height(22.dp)
            )

            Text(
                text = "Choisis un alphabet",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            AlphabetCard(
                japaneseTitle = "ひらがな",
                title = "Hiragana",
                description = "$hiraganaTotalCount caractères",
                knownCount = hiraganaKnownCount,
                totalCount = hiraganaTotalCount,
                dueCount = dueHiraganaCount,
                onClick = onHiraganaClick
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            AlphabetCard(
                japaneseTitle = "カタカナ",
                title = "Katakana",
                description = "$katakanaTotalCount caractères",
                knownCount = katakanaKnownCount,
                totalCount = katakanaTotalCount,
                dueCount = dueKatakanaCount,
                onClick = onKatakanaClick
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }
    }
}

@Composable
private fun DailyReviewCard(
    dueCount: Int,
    onClick: () -> Unit
) {
    val hasDueReviews = dueCount > 0

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = hasDueReviews,
                onClick = onClick
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (hasDueReviews) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 18.dp,
                vertical = 14.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(44.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surface
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "↻",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = if (hasDueReviews) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            }

            Spacer(
                modifier = Modifier.size(14.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Révisions du jour",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = if (hasDueReviews) {
                        "$dueCount kana à réviser"
                    } else {
                        "Tout est à jour pour le moment"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (hasDueReviews) {
                NavigationArrow()
            }
        }
    }
}

@Composable
private fun StatisticsEntryCard(
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 18.dp,
                vertical = 14.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(44.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "%",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            Spacer(
                modifier = Modifier.size(14.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Statistiques",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = "Consulte ta progression détaillée",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            NavigationArrow()
        }
    }
}

@Composable
private fun AppearanceEntryCard(
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 18.dp,
                vertical = 14.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(44.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.tertiaryContainer
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "◐",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }

            Spacer(
                modifier = Modifier.size(14.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Apparence",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = "Choisis le thème de l’application",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            NavigationArrow()
        }
    }
}

@Composable
private fun AlphabetCard(
    japaneseTitle: String,
    title: String,
    description: String,
    knownCount: Int,
    totalCount: Int,
    dueCount: Int,
    onClick: () -> Unit
) {
    val progress = calculateProgress(
        knownCount = knownCount,
        totalCount = totalCount
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick
            ),
        shape = RoundedCornerShape(22.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 14.dp
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier
                        .width(82.dp)
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = japaneseTitle,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.width(14.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(2.dp)
                    )

                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                NavigationArrow()
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$knownCount / $totalCount connus",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = if (dueCount > 0) {
                        "$dueCount à réviser"
                    } else {
                        "À jour"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(
                modifier = Modifier.height(7.dp)
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
}

@Composable
private fun NavigationArrow() {
    Surface(
        modifier = Modifier.size(30.dp),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "→",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun KanaGridScreen(
    title: String,
    japaneseTitle: String,
    kanaType: String,
    kanaList: List<KanaEntity>,
    knownCount: Int,
    dueReviewCount: Int,
    onKanaClick: (Int) -> Unit,
    onStartSession: (String) -> Unit,
    onStartQuiz: (String) -> Unit,
    onStartDueReviewSession: (String) -> Unit,
    onBackClick: () -> Unit
) {
    AppScaffold(
        title = title,
        onBack = onBackClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = japaneseTitle,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            ProgressCard(
                title = "Progression",
                knownCount = knownCount,
                totalCount = kanaList.size
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            PrimaryButton(
                onClick = {
                    onStartDueReviewSession(kanaType)
                },
                enabled = dueReviewCount > 0
            ) {
                Text(
                    text = if (dueReviewCount > 0) {
                        "Révisions du jour · $dueReviewCount"
                    } else {
                        "Aucune révision aujourd'hui"
                    },
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SecondaryButton(
                    onClick = {
                        onStartSession(kanaType)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Flashcards",
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                SecondaryButton(
                    onClick = {
                        onStartQuiz(kanaType)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Quiz",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            Text(
                text = "Tous les kana",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(
                    top = 4.dp,
                    bottom = 20.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(9.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp)
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
}

private fun calculateProgress(
    knownCount: Int,
    totalCount: Int
): Float {
    if (totalCount <= 0) {
        return 0f
    }

    return (
            knownCount.toFloat() / totalCount.toFloat()
            ).coerceIn(
            minimumValue = 0f,
            maximumValue = 1f
        )
}