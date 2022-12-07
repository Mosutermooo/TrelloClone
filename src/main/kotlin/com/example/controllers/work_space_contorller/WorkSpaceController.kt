package com.example.controllers.work_space_contorller

import com.example.models.BaseResponse
import com.example.models.work_space.WorkSpace
import com.example.models.work_space.WorkSpaceMember

interface WorkSpaceController {
    suspend fun createWorkSpace(workSpace: WorkSpace): BaseResponse
    suspend fun inviteMemberToWorkspace(params: WorkSpaceMember): BaseResponse
    suspend fun getUserWorkSpaces(userId: Int): BaseResponse
    suspend fun getWorkSpace(workSpaceId: String?): BaseResponse
}