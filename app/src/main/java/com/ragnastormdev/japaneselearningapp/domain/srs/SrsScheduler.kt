package com.ragnastormdev.japaneselearningapp.domain.srs

import com.ragnastormdev.japaneselearningapp.data.local.entity.KanaEntity
import java.time.Instant
import java.time.temporal.ChronoUnit

object SrsScheduler {

    fun calculateSuccess(
        kana: KanaEntity,
        currentTime: Long = System.currentTimeMillis()
    ): SrsReviewResult {
        val newSuccessfulReviewCount =
            kana.successfulReviewCount + 1

        val newIntervalDays = calculateNextIntervalDays(
            successfulReviewCount = newSuccessfulReviewCount,
            previousIntervalDays = kana.reviewIntervalDays
        )

        val nextReviewAt = Instant
            .ofEpochMilli(currentTime)
            .plus(
                newIntervalDays.toLong(),
                ChronoUnit.DAYS
            )
            .toEpochMilli()

        return SrsReviewResult(
            isKnown = true,
            nextReviewAt = nextReviewAt,
            successfulReviewCount = newSuccessfulReviewCount,
            reviewIntervalDays = newIntervalDays
        )
    }

    fun calculateFailure(
        currentTime: Long = System.currentTimeMillis()
    ): SrsReviewResult {
        return SrsReviewResult(
            isKnown = false,
            nextReviewAt = currentTime,
            successfulReviewCount = 0,
            reviewIntervalDays = 0
        )
    }

    private fun calculateNextIntervalDays(
        successfulReviewCount: Int,
        previousIntervalDays: Int
    ): Int {
        return when (successfulReviewCount) {
            1 -> 1
            2 -> 3
            3 -> 7
            4 -> 14
            5 -> 30

            else -> {
                previousIntervalDays
                    .coerceAtLeast(30)
                    .times(2)
            }
        }
    }
}

data class SrsReviewResult(
    val isKnown: Boolean,
    val nextReviewAt: Long,
    val successfulReviewCount: Int,
    val reviewIntervalDays: Int
)