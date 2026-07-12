package com.ragnastormdev.japaneselearningapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ragnastormdev.japaneselearningapp.data.local.dao.KanaDao
import com.ragnastormdev.japaneselearningapp.data.local.entity.KanaEntity

@Database(
    entities = [KanaEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun kanaDao(): KanaDao

    companion object {

        val MIGRATION_1_2 = object : Migration(1, 2) {

            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                    ALTER TABLE kana
                    ADD COLUMN isKnown INTEGER NOT NULL DEFAULT 0
                    """.trimIndent()
                )
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {

            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                    ALTER TABLE kana
                    ADD COLUMN nextReviewAt INTEGER DEFAULT NULL
                    """.trimIndent()
                )

                database.execSQL(
                    """
                    ALTER TABLE kana
                    ADD COLUMN successfulReviewCount INTEGER NOT NULL DEFAULT 0
                    """.trimIndent()
                )

                database.execSQL(
                    """
                    ALTER TABLE kana
                    ADD COLUMN reviewIntervalDays INTEGER NOT NULL DEFAULT 0
                    """.trimIndent()
                )
            }
        }
    }
}