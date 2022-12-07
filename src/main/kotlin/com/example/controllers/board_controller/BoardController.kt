package com.example.controllers.board_controller

import com.example.models.BaseResponse
import com.example.models.board.Board

interface BoardController {
    suspend fun createBoard(board: Board) : BaseResponse
}