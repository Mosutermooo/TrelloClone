package com.example.controllers.auth_controllers


import com.example.models.BaseResponse
import com.example.models.auth.NewTokenParams
import com.example.models.auth.RegisterParams

interface RegistrationController {
    suspend fun registerUser(params: RegisterParams) : BaseResponse
    suspend fun newToken(userId: Int?) : BaseResponse
}