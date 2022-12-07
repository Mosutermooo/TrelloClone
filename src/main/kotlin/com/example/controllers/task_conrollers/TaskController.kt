package com.example.controllers.task_conrollers

import com.example.models.BaseResponse
import com.example.models.board.Task

interface TaskController {

    suspend fun createTask(task: Task) : BaseResponse

}