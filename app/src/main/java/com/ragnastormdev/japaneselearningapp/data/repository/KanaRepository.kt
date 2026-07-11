package com.ragnastormdev.japaneselearningapp.data.repository

import com.ragnastormdev.japaneselearningapp.data.local.dao.KanaDao
import com.ragnastormdev.japaneselearningapp.data.local.entity.KanaEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

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