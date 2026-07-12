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

    @Query(
        """
        SELECT * FROM kana
        WHERE id = :id
        LIMIT 1
        """
    )
    fun getKanaById(id: Int): Flow<KanaEntity?>

    @Query(
        """
        UPDATE kana
        SET isKnown = :isKnown
        WHERE id = :kanaId
        """
    )
    suspend fun updateKnownStatus(
        kanaId: Int,
        isKnown: Boolean
    )

    @Query(
        """
        UPDATE kana
        SET isKnown = :isKnown,
            nextReviewAt = :nextReviewAt,
            successfulReviewCount = :successfulReviewCount,
            reviewIntervalDays = :reviewIntervalDays
        WHERE id = :kanaId
        """
    )
    suspend fun updateReviewProgress(
        kanaId: Int,
        isKnown: Boolean,
        nextReviewAt: Long?,
        successfulReviewCount: Int,
        reviewIntervalDays: Int
    )

    @Query(
        """
        SELECT * FROM kana
        WHERE type = :type
        AND nextReviewAt IS NOT NULL
        AND nextReviewAt <= :currentTime
        ORDER BY nextReviewAt ASC, displayOrder ASC
        """
    )
    fun getDueKanaByType(
        type: String,
        currentTime: Long
    ): Flow<List<KanaEntity>>

    @Query(
        """
        SELECT COUNT(*) FROM kana
        WHERE type = :type
        AND nextReviewAt IS NOT NULL
        AND nextReviewAt <= :currentTime
        """
    )
    fun observeDueKanaCountByType(
        type: String,
        currentTime: Long
    ): Flow<Int>

    @Query(
        """
        SELECT COUNT(*) FROM kana
        WHERE type = :type
        AND isKnown = 1
        """
    )
    fun observeKnownCountByType(type: String): Flow<Int>

    @Query(
        """
        SELECT COUNT(*) FROM kana
        WHERE isKnown = 1
        """
    )
    fun observeTotalKnownCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM kana")
    suspend fun getCount(): Int

    @Query("SELECT COUNT(*) FROM kana")
    fun observeCount(): Flow<Int>
}