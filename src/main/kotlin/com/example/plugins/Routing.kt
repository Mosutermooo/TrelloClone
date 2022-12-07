package com.example.plugins

import com.example.routes.authRoutes
import com.example.routes.boardRoutes
import com.example.routes.userRoutes
import com.example.routes.workSpaceRoutes
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {
    authRoutes()
    userRoutes()
    workSpaceRoutes()
    boardRoutes()
}
