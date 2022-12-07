package com.example.controllers.user_controller

import com.example.models.BaseResponse
import com.example.models.user.UpdateUserParams
import com.example.models.user.User

interface UserController {
    suspend fun getUser(uid: Int?) : BaseResponse
    suspend fun updateUser(params: UpdateUserParams): BaseResponse
}