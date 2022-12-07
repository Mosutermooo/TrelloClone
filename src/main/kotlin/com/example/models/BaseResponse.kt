
package com.example.models


import com.example.models.user.User
import com.example.models.work_space.WorkSpace
import com.example.routes.Exceptions


sealed class BaseResponse() {

    data class GoodResponse(
        val data: Response? = null,
        val  message: String? = null
    ) : BaseResponse()

    data class BadResponse(
        val data: Response? = null,
        val message: String? = null,
        val exception: Exceptions?
    ) : BaseResponse()

}

sealed class Response{
    data class UserResponse (
        val user: User
    ): Response()
    data class TokenResponse(
        val token: String,
        val uid: Int
    ): Response()
    data class GoodBadResponse(
        val success: Boolean,
        val message: String
    ): Response()
    data class ListWorkspaceResponse(
        val workspaces: List<WorkSpace?>
    ): Response()
}



