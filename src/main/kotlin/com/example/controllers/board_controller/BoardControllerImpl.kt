package com.example.controllers.board_controller

import com.example.database.Database.dbQuery
import com.example.database.tables.BoardTable
import com.example.database.tables.WorkSpaceTable
import com.example.mappers.WorkSpaceMapper
import com.example.models.BaseResponse
import com.example.models.Response
import com.example.models.board.Board
import com.example.routes.WorkSpaceDoesntExistException
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class BoardControllerImpl(
    private val workSpaceMapper: WorkSpaceMapper
) : BoardController {


    override suspend fun createBoard(board: Board): BaseResponse {

       dbQuery {
            WorkSpaceTable.select {
                WorkSpaceTable.workSpaceId.eq(board.worksSpaceId)
            }.map {
                workSpaceMapper.fromRowToT(it)
            }
        }.singleOrNull()
            ?: return BaseResponse.BadResponse(
                Response.GoodBadResponse(
                    false, "The workspace you are trying to add doesn't exist"
                ),
                "unsuccessful",
                WorkSpaceDoesntExistException()
            )


        val insertStatement = dbQuery{
            BoardTable.insert {
                it[BoardTable.name] = board.name
                it[BoardTable.visibility] = board.visibility
                it[BoardTable.workSpaceId] = board.worksSpaceId
                it[BoardTable.backgroundImage] = board.backgroundImage
            }
        }

        if(insertStatement.insertedCount == 1){
            return BaseResponse.GoodResponse(Response.GoodBadResponse(true, "Successfully created board"), "success")
        }
        return BaseResponse.BadResponse(Response.GoodBadResponse(false, "Something went wrong, try again"), "unsuccessful", null)
    }




}