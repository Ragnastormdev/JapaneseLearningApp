package com.ragnastormdev.japaneselearningapp.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ragnastormdev.japaneselearningapp.data.preferences.ThemeMode
import com.ragnastormdev.japaneselearningapp.ui.viewmodel.ThemeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearanceScreen(
    onBackClick: () -> Unit,
    themeViewModel: ThemeViewModel = hiltViewModel()
) {
    val selectedThemeMode by themeViewModel.themeMode.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Apparence")
                },
                navigationIcon = {
                    Text(
                        text = "←",
                        modifier = Modifier
                            .clickable(onClick = onBackClick)
                            .padding(16.dp)
                    )
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Choisissez le thème de l’application"
            )

            ThemeModeCard(
                title = "Suivre le système",
                description = "Utilise automatiquement le thème clair ou sombre du téléphone.",
                selected = selectedThemeMode == ThemeMode.SYSTEM,
                onClick = {
                    themeViewModel.setThemeMode(ThemeMode.SYSTEM)
                }
            )

            ThemeModeCard(
                title = "Clair",
                description = "Utilise toujours le thème clair.",
                selected = selectedThemeMode == ThemeMode.LIGHT,
                onClick = {
                    themeViewModel.setThemeMode(ThemeMode.LIGHT)
                }
            )

            ThemeModeCard(
                title = "Sombre",
                description = "Utilise toujours le thème sombre.",
                selected = selectedThemeMode == ThemeMode.DARK,
                onClick = {
                    themeViewModel.setThemeMode(ThemeMode.DARK)
                }
            )
        }
    }
}

@Composable
private fun ThemeModeCard(
    title: String,
    description: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer
            } else {
                androidx.compose.material3.MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selected,
                onClick = onClick
            )

            Column(
                modifier = Modifier.padding(start = 12.dp)
            ) {
                Text(
                    text = title,
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                )

                Text(
                    text = description,
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}