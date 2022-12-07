package com.example.models.work_space

import com.example.models.user.User
import java.util.UUID

data class WorkSpace(
    val workSpaceId: String = UUID.randomUUID().toString(),
    val adminUId: Int,
    val name: String,
    val type: String,
    val description: String,
    val workSpaceMembers: List<Member?> = emptyList()
)
