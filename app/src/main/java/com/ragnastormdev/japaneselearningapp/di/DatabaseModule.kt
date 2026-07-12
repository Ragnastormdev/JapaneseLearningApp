package com.ragnastormdev.japaneselearningapp.di

import android.content.Context
import androidx.room.Room
import com.ragnastormdev.japaneselearningapp.data.local.dao.KanaDao
import com.ragnastormdev.japaneselearningapp.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "japanese_learning_database"
        )
            .addMigrations(
                AppDatabase.MIGRATION_1_2
            )
            .build()
    }

    @Provides
    fun provideKanaDao(
        database: AppDatabase
    ): KanaDao {
        return database.kanaDao()
    }
}