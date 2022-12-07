package com.example.plugins.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.example.models.auth.RegisterParams
import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import org.koin.ktor.ext.inject
import java.util.*



fun Application.configureSecurity(){


    val tokenConfig by inject<TokenConfig>()

    install(Authentication){
        jwt {
            realm = "Trello-Clone-Api"
            verifier(
                JWT.require(Algorithm.HMAC256(tokenConfig.secret))
                    .withAudience(tokenConfig.audience)
                    .withIssuer(tokenConfig.issuer)
                    .build()
            )
            validate {credential->
                if(credential.payload.audience.contains(tokenConfig.audience)){
                    JWTPrincipal(credential.payload)
                }else null
            }
        }
    }
}