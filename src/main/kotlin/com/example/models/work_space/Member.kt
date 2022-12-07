package com.example.models.work_space

data class Member(
    val uid: Int,
    val name: String,
    val lastname: String,
    val tag: String,
    val email: String,
    val phoneNumber: String,
    val memberStatus: String,
    val workSpaceId: String
)
