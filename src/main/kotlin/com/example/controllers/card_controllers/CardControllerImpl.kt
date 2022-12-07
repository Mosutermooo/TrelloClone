package com.example.controllers.card_controllers

import com.example.database.Database.dbQuery
import com.example.database.tables.CardTable
import com.example.models.BaseResponse
import com.example.models.Response
import com.example.models.board.Card
import org.jetbrains.exposed.sql.insert

class CardControllerImpl : CardController {
    override suspend fun createCard(card: Card): BaseResponse {
        val insertStatement = dbQuery{
            CardTable.insert {
                it[CardTable.boardId] = card.boardId
                it[CardTable.name] = card.name
            }
        }
        if(insertStatement.insertedCount == 1){
            return BaseResponse.GoodResponse(Response.GoodBadResponse(true, "Successfully created card"), "success")
        }
        return BaseResponse.BadResponse(Response.GoodBadResponse(false, "Something went wrong, try again"), "unsuccessful", null)
    }
}