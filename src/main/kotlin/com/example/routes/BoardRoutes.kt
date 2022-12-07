package com.example.routes

import com.example.controllers.board_controller.BoardController
import com.example.controllers.card_controllers.CardController
import com.example.controllers.task_conrollers.TaskController
import com.example.models.board.Board
import com.example.models.board.Card
import com.example.models.board.Task
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.boardRoutes(){

    val boardController by inject<BoardController>()
    val cardController by inject<CardController>()
    val taskController by inject<TaskController>()


    routing {
        route("/board"){

            post("/create"){
                val board = call.receive<Board>()
                boardController.createBoard(board).let {
                    call.respond(it)
                }
            }

            post("/create_card") {
                val card = call.receive<Card>()
                cardController.createCard(card).let {
                    call.respond(it)
                }
            }

            post("/create_task") {
                val task = call.receive<Task>()
                taskController.createTask(task).let {
                    call.respond(it)
                }
            }

        }
    }

}