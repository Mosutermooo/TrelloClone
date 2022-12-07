package com.example.controllers.card_controllers

import com.example.models.BaseResponse
import com.example.models.board.Card

interface CardController {
    suspend fun createCard(card: Card): BaseResponse
}