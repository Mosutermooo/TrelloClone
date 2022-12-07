package com.example.models.board

data class Board(
    val boardId: Int,
    val name: String,
    val worksSpaceId: String,
    val backgroundImage: String,
    val visibility: String,
    val boards: List<Board> = emptyList()
)
