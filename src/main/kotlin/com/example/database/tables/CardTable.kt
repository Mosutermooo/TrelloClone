package com.example.database.tables

import com.example.models.board.Task
import org.jetbrains.exposed.sql.Table

object CardTable : Table("card_table") {

    val cardId = integer("cardId").autoIncrement()
    val boardId = integer("boardId")
    val name = varchar("name", 200)
    override val primaryKey: PrimaryKey = PrimaryKey(cardId)

}


