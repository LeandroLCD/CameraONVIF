package com.leandrolcd.cameraonvif.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.leandrolcd.cameraonvif.ui.player.PlayerScreen
import com.leandrolcd.cameraonvif.ui.utilities.toBitmap

@ExperimentalComposeUiApi
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val snapshot = viewModel.image.collectAsState().value
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (snapshot == null) {
                TopAppBar(title = { Text("ONVIF Camera Demo") })
            } else {
                TopAppBar(
                    title = { Text("Snapshot") },
                    navigationIcon = {
                        IconButton(onClick = { viewModel.clearSnapshot() }) {
                            Icon(Icons.Default.Close, null)
                        }
                    }
                )
            }
        }
    ) { padding ->
        val isPlay = remember { mutableStateOf(false) }
            if(!isPlay.value){
                if (snapshot == null) {
                    Column(Modifier.padding(padding)) {
                        var scanning by remember { mutableStateOf(false) }
                        if (scanning) {
                            val discoveredDevices by viewModel.discoveredDevices.collectAsState(emptyList())
                            LazyColumn(Modifier.background(Color.Red)) {
                                items(
                                    items = discoveredDevices,
                                    key = { it.id },
                                ) {
                                    Box(
                                        Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                scanning = false
                                                viewModel.address.value = it.host
                                            }
                                    ) {
                                        Text(it.friendlyName ?: it.id)
                                    }
                                }
                            }
                            Button(onClick = { scanning = false }) {
                                Text("Cancel")
                            }
                        } else {
                            Button(onClick = { scanning = true }) {
                                Text("Scan")
                            }
                        }

                        val address by viewModel.address
                        TextField(
                            value = address,
                            onValueChange = { viewModel.address.value = it },
                            label = { Text("Address") },
                            singleLine = true,
                        )

                        val username by viewModel.login
                        TextField(
                            value = username,
                            onValueChange = { viewModel.login.value = it },
                            label = { Text("Username") },
                            singleLine = true,
                        )

                        val password by viewModel.password
                        TextField(
                            value = password,
                            onValueChange = { viewModel.password.value = it },
                            label = { Text("Password") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//                visualTransformation = PasswordVisualTransformation(),
                        )

                        Button(
                            onClick = viewModel::connectClicked,
                            enabled = address.isNotBlank()
                        ) {
                            Text("Connect")
                        }


                    }
                } else {
                    Image(snapshot.toBitmap().asImageBitmap(), null)
                }
            }else{
                val uri = viewModel.snapshotUri.value
                if(uri != null){
                    PlayerScreen(uri)
                }else{
                    Box(modifier = Modifier.background(Color.Red).fillMaxSize())
                }
                

            }


    }

    val errorText = viewModel.errorText.collectAsState().value
    LaunchedEffect(errorText) {
        if (errorText != null) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = errorText,
                actionLabel = "Dismiss",
                duration = SnackbarDuration.Long
            )
            viewModel.clearErrorText()
        }
    }
}

