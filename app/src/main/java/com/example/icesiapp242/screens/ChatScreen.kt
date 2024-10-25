package com.example.icesiapp242.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.icesiapp242.viewmodel.ChatViewModel

@Composable
fun ChatScreen(navController: NavController, chatViewModel: ChatViewModel = viewModel()) {
    var otherUserID by remember { mutableStateOf("9hHd0aWTwBN5PA3QB71gRDudxdw2") }
    var messageText by remember { mutableStateOf("") }
    val messagesState by chatViewModel.messagesState.observeAsState()

    Log.e(">>>STATE", messagesState?.size.toString())


    LaunchedEffect(true) {
        chatViewModel.getMessagesLiveMode(otherUserID)
    }


    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                messagesState?.let {
                    items(it) { message ->
                        Text(text = message?.content ?: "")
                    }
                }
            }
            Row {
                TextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier.weight(1f)
                )
                Button(onClick = { chatViewModel.sendMessage(messageText, otherUserID) }) {
                    Text(text = "Enviar")
                }
            }

        }
    }
}