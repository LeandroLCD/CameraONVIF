package com.leandrolcd.cameraonvif.exoplayer

import android.media.metrics.PlaybackStateEvent.STATE_BUFFERING
import android.util.Log
import androidx.media3.common.Player
import androidx.media3.common.Player.STATE_ENDED
import androidx.media3.common.Player.STATE_READY
import androidx.media3.exoplayer.ExoPlayer
import com.leandrolcd.cameraonvif.domain.state.PlayerState
import com.leandrolcd.cameraonvif.domain.state.PlayerStateListener

class ExoPlayerStateListener(
    private val stateListener: PlayerStateListener,
    val player: ExoPlayer
) : Player.Listener {

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        stateListener.on(getStateWhen(isPlaying))
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        val state = when (playbackState) {
            STATE_BUFFERING -> PlayerState.Buffering
            STATE_READY -> {
                val isPlaying = player.playWhenReady
                getStateWhen(isPlaying)
            }

            STATE_ENDED -> PlayerState.Complete
            else -> PlayerState.Idle
        }

        Log.d("exoplayer","PlaybackState $state")
        stateListener.on(state)
    }

    private fun getStateWhen(playing: Boolean) = if (playing)
        PlayerState.Playing
    else
        PlayerState.Pause
}