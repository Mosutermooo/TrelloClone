package com.example.plugins.security

interface TokenService {
    fun generate(
        config: TokenConfig,
        claims: ArrayList<TokenClaim>
    ): String
}