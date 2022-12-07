package com.example

import com.example.database.Database
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import com.example.plugins.security.configureSecurity

fun main() {
    embeddedServer(Netty, port = 8081) {
        Database.connect()
        module()
    }.start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureSockets()
    configureRouting()
    configureKoin()
    configureSecurity()
}
