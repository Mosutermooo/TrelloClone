package com.example.controllers.task_conrollers

import com.example.database.Database.dbQuery
import com.example.database.tables.TaskTable
import com.example.models.BaseResponse
import com.example.models.Response
import com.example.models.board.Task
import org.jetbrains.exposed.sql.insert

class TaskControllerImpl : TaskController {
    override suspend fun createTask(task: Task): BaseResponse {
        val insertStatement = dbQuery {
            TaskTable.insert {
                it[TaskTable.cardId] = task.cardId
                it[TaskTable.text] = task.text
                it[TaskTable.description] = task.description
                it[TaskTable.labelColor] = task.labelColor
                it[TaskTable.timeStamp] = task.timeStamp
            }
        }


        return if(insertStatement.insertedCount == 1){
            BaseResponse.GoodResponse(
                Response.GoodBadResponse(
                    true,
                    "Successfully created task"
                )
            )
        }else{
            BaseResponse.BadResponse(Response.GoodBadResponse(false, "Something went wrong, try again"), "unsuccessful", null)
        }

    }
}