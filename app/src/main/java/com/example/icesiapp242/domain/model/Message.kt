package com.example.icesiapp242.domain.model

import com.google.firebase.Timestamp

data class Message(
    var id: String = "",
    var content: String = "",
    var date: Timestamp = Timestamp.now()
)