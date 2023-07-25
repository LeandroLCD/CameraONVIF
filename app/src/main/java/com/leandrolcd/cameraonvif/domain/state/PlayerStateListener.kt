package com.leandrolcd.cameraonvif.domain.state

interface PlayerStateListener{
    fun on(state: PlayerState)
}