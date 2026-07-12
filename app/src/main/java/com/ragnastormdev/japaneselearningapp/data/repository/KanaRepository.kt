package com.ragnastormdev.japaneselearningapp.data.repository

import com.ragnastormdev.japaneselearningapp.data.local.dao.KanaDao
import com.ragnastormdev.japaneselearningapp.data.local.entity.KanaEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class KanaRepository @Inject constructor(
    private val kanaDao: KanaDao
) {

    fun getKanaByType(type: String): Flow<List<KanaEntity>> {
        return kanaDao.getKanaByType(type)
    }

    fun getKanaById(id: Int): Flow<KanaEntity?> {
        return kanaDao.getKanaById(id)
    }

    suspend fun updateKnownStatus(
        kanaId: Int,
        isKnown: Boolean
    ) {
        kanaDao.updateKnownStatus(
            kanaId = kanaId,
            isKnown = isKnown
        )
    }

    suspend fun updateReviewProgress(
        kanaId: Int,
        isKnown: Boolean,
        nextReviewAt: Long?,
        successfulReviewCount: Int,
        reviewIntervalDays: Int
    ) {
        kanaDao.updateReviewProgress(
            kanaId = kanaId,
            isKnown = isKnown,
            nextReviewAt = nextReviewAt,
            successfulReviewCount = successfulReviewCount,
            reviewIntervalDays = reviewIntervalDays
        )
    }

    fun getDueKanaByType(
        type: String,
        currentTime: Long
    ): Flow<List<KanaEntity>> {
        return kanaDao.getDueKanaByType(
            type = type,
            currentTime = currentTime
        )
    }

    fun observeDueKanaCountByType(
        type: String,
        currentTime: Long
    ): Flow<Int> {
        return kanaDao.observeDueKanaCountByType(
            type = type,
            currentTime = currentTime
        )
    }

    fun observeKnownCountByType(type: String): Flow<Int> {
        return kanaDao.observeKnownCountByType(type)
    }

    fun observeTotalKnownCount(): Flow<Int> {
        return kanaDao.observeTotalKnownCount()
    }

    suspend fun insertAll(kana: List<KanaEntity>) {
        kanaDao.insertAll(kana)
    }

    suspend fun getCount(): Int {
        return kanaDao.getCount()
    }

    fun observeCount(): Flow<Int> {
        return kanaDao.observeCount()
    }
}