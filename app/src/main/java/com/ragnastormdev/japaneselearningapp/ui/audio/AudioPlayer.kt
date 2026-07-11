package com.ragnastormdev.japaneselearningapp.ui.audio

import android.content.Context
import android.media.MediaPlayer

class AudioPlayer(
    private val context: Context
) {

    private var mediaPlayer: MediaPlayer? = null

    fun play(fileName: String) {
        stop()

        val resourceId = context.resources.getIdentifier(
            fileName,
            "raw",
            context.packageName
        )

        if (resourceId == 0) {
            return
        }

        mediaPlayer = MediaPlayer.create(
            context,
            resourceId
        )

        mediaPlayer?.setOnCompletionListener {
            stop()
        }

        mediaPlayer?.start()
    }

    fun stop() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}