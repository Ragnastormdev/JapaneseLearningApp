package com.ragnastormdev.japaneselearningapp.data.local.source

import android.content.Context
import com.ragnastormdev.japaneselearningapp.data.local.entity.KanaEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONArray
import javax.inject.Inject

class KanaJsonDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun loadHiragana(): List<KanaEntity> {
        return loadKanaFromFile("hiragana.json")
    }

    fun loadKatakana(): List<KanaEntity> {
        return loadKanaFromFile("katakana.json")
    }

    private fun loadKanaFromFile(fileName: String): List<KanaEntity> {
        val json = context.assets
            .open(fileName)
            .bufferedReader()
            .use { reader -> reader.readText() }

        val jsonArray = JSONArray(json)

        return buildList {
            for (index in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(index)

                add(
                    KanaEntity(
                        id = item.getInt("id"),
                        character = item.getString("character"),
                        romaji = item.getString("romaji"),
                        type = item.getString("type"),
                        displayOrder = item.getInt("displayOrder")
                    )
                )
            }
        }
    }
}