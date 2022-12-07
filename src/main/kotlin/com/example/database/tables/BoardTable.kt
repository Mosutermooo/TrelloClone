package com.example.database.tables

import com.example.models.board.Task
import org.jetbrains.exposed.sql.Table

object BoardTable : Table("board_table") {

    val boardId = integer("board_id").autoIncrement()
    val name = varchar("name", 200)
    val workSpaceId = varchar("work_space_id", 200)
    val backgroundImage = varchar("background_image", 2500)
    val visibility = varchar("visibility", 200)
    override val primaryKey: PrimaryKey = PrimaryKey(boardId)


}
