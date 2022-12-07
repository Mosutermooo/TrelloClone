package com.example.models.board

data class Card(
    val cardId: Int,
    val boardId: Int,
    val name: String,
    val tasks: List<Task> = emptyList()
)
