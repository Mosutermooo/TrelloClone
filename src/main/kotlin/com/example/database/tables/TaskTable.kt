package com.example.database.tables

import org.jetbrains.exposed.sql.Table

object TaskTable : Table("task_table") {

    val taskId = integer("taskId").autoIncrement()
    val cardId = integer("boardId")
    val text = varchar("text", 10000)
    val timeStamp = varchar("time_stamp", 2000)
    val description = varchar("description", 1000)
    val labelColor = varchar("label_color", 150)
    override val primaryKey: PrimaryKey = PrimaryKey(taskId)

}


