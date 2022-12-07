package com.example.mappers

import com.example.database.tables.TaskTable
import com.example.models.board.Task
import org.jetbrains.exposed.sql.ResultRow

class TaskMapper : Mapper<Task?> {
    override fun fromRowToT(row: ResultRow?): Task? {
        return if(row == null) null
        else {
            Task(
                taskId = row[TaskTable.taskId],
                cardId = row[TaskTable.cardId],
                text = row[TaskTable.text],
                timeStamp = row[TaskTable.timeStamp],
                description = row[TaskTable.description],
                labelColor = row[TaskTable.labelColor]
            )
        }
    }
}