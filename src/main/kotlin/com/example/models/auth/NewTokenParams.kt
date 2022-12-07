package com.example.models.auth

data class NewTokenParams (
    val name: String,
    val lastname: String,
    val email: String,
    val token: String? = null
        )