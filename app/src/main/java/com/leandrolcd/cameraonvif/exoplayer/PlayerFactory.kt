package com.leandrolcd.cameraonvif.exoplayer

import android.annotation.SuppressLint
import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.leandrolcd.cameraonvif.domain.TLPlayer
import java.lang.ref.WeakReference

object PlayerFactory {
    @SuppressLint("UnsafeOptInUsageError")
    fun create(
        context: Context,
    ): TLPlayer = ExoPlayerImpl(WeakReference(context), ExoPlayer.Builder(context).build())
}