package com.ragnastormdev.japaneselearningapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ragnastormdev.japaneselearningapp.data.local.entity.KanaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface KanaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(kana: List<KanaEntity>)

    @Query(
        """
        SELECT * FROM kana
        WHERE type = :type
        ORDER BY displayOrder ASC
        """
    )
    fun getKanaByType(type: String): Flow<List<KanaEntity>>
}