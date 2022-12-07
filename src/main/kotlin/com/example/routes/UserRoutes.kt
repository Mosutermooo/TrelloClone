package com.example.routes

import com.example.controllers.user_controller.UserController
import com.example.models.user.UpdateUserParams
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.userRoutes(){

    val userController by inject<UserController>()

    routing {
        authenticate {
            route("/user"){
                get("/getUser/{uid}") {
                    val uid = call.parameters["uid"]?.toIntOrNull()
                    val response = userController.getUser(uid)
                    call.respond(response)
                }
                post("/updateUser") {
                    val uid = call.request.queryParameters["uid"]?.toIntOrNull()
                    val name = call.request.queryParameters["name"]
                    val lastname = call.request.queryParameters["lastname"]
                    val phoneNumber = call.request.queryParameters["phoneNumber"]

                    UpdateUserParams(
                        uid,
                        phoneNumber,
                        name,
                        lastname
                    ).let {
                        val response = userController.updateUser(it)
                        call.respond(response)
                    }

                }
            }
        }
    }
}