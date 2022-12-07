package com.example.plugins.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm

class JwtTokenServiceImpl : TokenService {

    override fun generate(config: TokenConfig, claims: ArrayList<TokenClaim>): String {
       var token =  JWT.create()
           .withAudience(config.audience)
           .withIssuer(config.issuer)

        claims.forEach {claim->
            token = token.withClaim(claim.name, claim.value)
        }

        return token.sign(Algorithm.HMAC256(config.secret))
    }

}