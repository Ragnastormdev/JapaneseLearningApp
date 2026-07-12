package com.ragnastormdev.japaneselearningapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kana")
data class KanaEntity(
    @PrimaryKey
    val id: Int,
    val character: String,
    val romaji: String,
    val type: String,
    val displayOrder: Int,
    val isKnown: Boolean = false
)