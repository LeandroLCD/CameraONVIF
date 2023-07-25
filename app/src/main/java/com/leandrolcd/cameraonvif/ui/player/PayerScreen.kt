package com.leandrolcd.cameraonvif.ui.player

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import com.leandrolcd.cameraonvif.domain.TLPlayer
import com.leandrolcd.cameraonvif.exoplayer.PlayerFactory


@ExperimentalComposeUiApi
@Composable
fun PlayerScreen(url:String){

    CustomPlayerScreen(url = url)

}



@SuppressLint("ClickableViewAccessibility")
@ExperimentalComposeUiApi
@Composable
fun CustomPlayerScreen(url: String) {
    Log.d(TAG, "CustomPlayerScreen: $url")
    // Aquí creas tu instancia de TLPlayer con PlayerFactory
    val playerFactory = PlayerFactory.create(context = LocalContext.current)
    val tlPlayer: TLPlayer = playerFactory
    tlPlayer.prepare(url, true) // Configurar la URL del video para reproducir
    tlPlayer.play()
    Log.d(TAG, "CustomPlayerScreen: ${tlPlayer.play()}")
    var areControlsVisible by remember { mutableStateOf(true) }

    val handler = Handler(Looper.getMainLooper())
    val runnable = {
        areControlsVisible = false
    }

    fun registerHandler() {
        handler.postDelayed(runnable, 5000)
    }

    // Ejecutar el registro en LaunchedEffect
    LaunchedEffect(Unit) {
        registerHandler()
    }

    // Mostrar los controles cuando hay interacción del usuario
    LocalView.current.setOnTouchListener { _, _ ->
        areControlsVisible = true
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, 5000)
        false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // AndroidView para el reproductor ExoPlayer
        AndroidView(
            factory = { context ->
                val playerView = PlayerView(context)
                playerView.player = tlPlayer.exoPlayer() // Asignar el reproductor de TLPlayer
                playerView
            },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        )

        // Controles en la parte inferior
        if (areControlsVisible) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
                    .background(Color.Black.copy(alpha = 0.6f)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Aquí puedes agregar tus controles personalizados
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Control para acceder a grabaciones previas
                    IconButton(onClick = { /* Acción al hacer clic */ }) {
                        Icon(
                            imageVector = Icons.Default.PlayCircleFilled,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                    // Control para silenciar el audio
                    IconButton(onClick = { /* Acción al hacer clic */ }) {
                        Icon(
                            imageVector = Icons.Default.VolumeMute,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                    // Control para hablar por la cámara
                    IconButton(onClick = { /* Acción al hacer clic */ }) {
                        Icon(
                            imageVector = Icons.Default.PhoneAndroid,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                    // Control para configurar los valores de la cámara
                    IconButton(onClick = { /* Acción al hacer clic */ }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}


