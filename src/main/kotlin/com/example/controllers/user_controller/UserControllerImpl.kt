package com.example.controllers.user_controller

import com.example.database.Database.dbQuery
import com.example.mappers.UserMapper
import com.example.models.BaseResponse
import com.example.models.Response
import com.example.models.user.UpdateUserParams
import com.example.routes.InvalidUserIdentifierException
import com.example.routes.UserDoesntExistsException
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update

class UserControllerImpl(
    private val userMapperImpl: UserMapper,
    ) : UserController {

    override suspend fun getUser(uid: Int?): BaseResponse {
        if(uid == null){
            return BaseResponse.BadResponse(
                null, "Invalid UserId", InvalidUserIdentifierException()
            )
        }

        val user = dbQuery {
            com.example.database.tables.User.select {
                com.example.database.tables.User.uid.eq(uid)
            }.map {
                userMapperImpl.fromRowToT(it)
            }
        }.singleOrNull() ?: return BaseResponse.BadResponse(
                null, "error", UserDoesntExistsException()
        )

        return BaseResponse.GoodResponse(
             Response.UserResponse(user),
            "User"
        )



    }

    override suspend fun updateUser(params: UpdateUserParams): BaseResponse {
        if(params.uid == null){
            return BaseResponse.BadResponse(
                null, "invalid uid", InvalidUserIdentifierException()
            )
        }

        dbQuery {
            com.example.database.tables.User.select {
                com.example.database.tables.User.uid.eq(params.uid)
            }.map {
                userMapperImpl.fromRowToT(it)
            }
        }.singleOrNull() ?: return BaseResponse.BadResponse(
            null, "error", UserDoesntExistsException()
        )


       if(params.phoneNumber != null && params.phoneNumber != ""){
           dbQuery {
               com.example.database.tables.User.update({com.example.database.tables.User.uid.eq(params.uid)}){
                   it[com.example.database.tables.User.phoneNumber] = params.phoneNumber
               }
           }
        }
        if(params.name != null && params.name != ""){
           dbQuery {
               com.example.database.tables.User.update({com.example.database.tables.User.uid.eq(params.uid)}){
                   it[com.example.database.tables.User.name] = params.name
               }
           }

        }
        if(params.lastname != null && params.lastname != "" ){
           dbQuery {
               com.example.database.tables.User.update({com.example.database.tables.User.uid.eq(params.uid)}){
                   it[com.example.database.tables.User.lastname] = params.lastname
               }
           }
        }

        return  BaseResponse.GoodResponse(
            Response.GoodBadResponse(true, "User Updated"), message = null
        )
    }

}