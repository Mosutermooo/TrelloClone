package com.example.models.board

data class Task(
    val taskId: Int,
    val cardId: Int,
    val text: String,
    val timeStamp: String,
    val description: String,
    val labelColor: String
)
