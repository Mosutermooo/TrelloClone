package com.example.routes

import com.example.controllers.auth_controllers.RegistrationController
import com.example.models.auth.NewTokenParams
import com.example.models.auth.RegisterParams
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Application.authRoutes(){

    val registrationController by inject<RegistrationController>()

    routing() {
       route("/auth"){
           post("/register") {
               val registerParams = call.receive<RegisterParams>()
               val response = registrationController.registerUser(registerParams)
               call.respond(response)
           }

           get("/newToken/{uid}") {
               val uid = call.parameters["uid"]?.toInt()
               val response = registrationController.newToken(uid)
               call.respond(response)
           }

       }
    }


}