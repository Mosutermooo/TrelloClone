package com.example.controllers.auth_controllers

import com.example.database.Database.dbQuery
import com.example.database.tables.User
import com.example.mappers.UserMapper
import com.example.models.BaseResponse
import com.example.models.Response
import com.example.models.auth.RegisterParams
import com.example.plugins.security.TokenClaim
import com.example.plugins.security.TokenConfig
import com.example.plugins.security.TokenService
import com.example.routes.InvalidUserIdentifierException
import com.example.routes.UserAlreadyExistsWithEmailException
import com.example.routes.UserDoesntExistsException
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class RegistrationControllerImpl(
    private val userMapperImpl: UserMapper,
    private val tokenService: TokenService,
    private val tokenConfig: TokenConfig
) : RegistrationController {



    override suspend fun registerUser(params: RegisterParams): BaseResponse {


        val user = dbQuery {
            User.select {
                User.email.eq(params.email)
            }.map {
                userMapperImpl.fromRowToT(it)
            }
        }.singleOrNull()

        if(user != null){
            return BaseResponse.BadResponse(
                null,
                "User Already Exists",
                UserAlreadyExistsWithEmailException()
            )
        }

       dbQuery {
            User.insert {
                it[User.name] = params.name
                it[User.lastname] = params.lastname
                it[User.email] = params.email
                it[User.tag] = "@${params.email.split('@')[0]}"
                it[User.phoneNumber] = ""
            }
        }


        val newUser = dbQuery {
            User.select {
                User.email.eq(params.email)
            }.map {
                userMapperImpl.fromRowToT(it)
            }
        }.singleOrNull()

        return newToken(newUser?.uid)

    }


    override suspend fun newToken(userId: Int?): BaseResponse {

        if(userId == null){
            return BaseResponse.BadResponse(
                null,
                "Invaild uid",
                InvalidUserIdentifierException()
            )
        }

        val user = dbQuery {
            User.select {
                User.uid.eq(userId)
            }.map {
                userMapperImpl.fromRowToT(it)
            }
        }.singleOrNull()
            ?: return BaseResponse.BadResponse(
                null, "error", UserDoesntExistsException()
            )


        val token = tokenService.generate(
            tokenConfig,
            arrayListOf<TokenClaim>(
                TokenClaim("uid", user.uid.toString()),
                TokenClaim("email", user.email),
                TokenClaim("creation_date", System.currentTimeMillis().toString())
            )
        )
        return BaseResponse.GoodResponse(Response.TokenResponse(token, user.uid), "successfully generated token")
    }





}