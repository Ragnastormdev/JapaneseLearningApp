package com.ragnastormdev.japaneselearningapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ragnastormdev.japaneselearningapp.data.local.dao.KanaDao
import com.ragnastormdev.japaneselearningapp.data.local.entity.KanaEntity

@Database(
    entities = [KanaEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun kanaDao(): KanaDao
}