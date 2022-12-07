package com.example.controllers.work_space_contorller

import com.example.database.Database.dbQuery
import com.example.database.tables.User
import com.example.database.tables.WorkSpaceTable
import com.example.database.tables.WorkSpaceMembersTable
import com.example.mappers.MemberMapper
import com.example.mappers.UserMapper
import com.example.mappers.WorkSpaceMapper
import com.example.mappers.WorkSpacesMembersMapper
import com.example.models.BaseResponse
import com.example.models.Response
import com.example.models.work_space.Member
import com.example.models.work_space.MemberStatus
import com.example.models.work_space.WorkSpace
import com.example.models.work_space.WorkSpaceMember
import com.example.routes.UserAlreadyAddedToWorkSpaceException
import com.example.routes.UserDoesntExistsException
import com.example.routes.WorkSpaceDoesntExistException
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class WorkSpaceControllerImpl(
    private val workSpaceMapper: WorkSpaceMapper,
    private val workSpacesMembersMapper: WorkSpacesMembersMapper,
    private val userMapper: UserMapper,
    private val memberMapper: MemberMapper
) : WorkSpaceController {

    override suspend fun createWorkSpace(workSpace: WorkSpace): BaseResponse {
        dbQuery{
            WorkSpaceTable.insert {
                it[WorkSpaceTable.workSpaceId] = workSpace.workSpaceId
                it[WorkSpaceTable.adminUId] = workSpace.adminUId
                it[WorkSpaceTable.name] = workSpace.name
                it[WorkSpaceTable.description] = workSpace.description
                it[WorkSpaceTable.type] = workSpace.type
            }
        }

        val newWorkSpace = dbQuery {
            WorkSpaceTable.select{
                WorkSpaceTable.workSpaceId.eq(workSpace.workSpaceId)
            }.map {
                workSpaceMapper.fromRowToT(it)
            }.singleOrNull()
        } ?: return BaseResponse.BadResponse(
                Response.GoodBadResponse(
                    false, "Work space does not exist"
                ),"error", WorkSpaceDoesntExistException()
            )


        WorkSpaceMember(
            workSpaceId = newWorkSpace.workSpaceId,
            uid = newWorkSpace.adminUId,
            status = MemberStatus.ADMIN.name
        ).let {
            inviteMemberToWorkspace(it)
        }

        return BaseResponse.GoodResponse(
            Response.GoodBadResponse(true, "Work Space Create"), null
        )
    }

    override suspend fun inviteMemberToWorkspace(params: WorkSpaceMember): BaseResponse {

        dbQuery {
            User.select{
                User.uid.eq(params.uid)
            }.map {
                userMapper.fromRowToT(it)
            }.singleOrNull()
        } ?: return BaseResponse.BadResponse(
            Response.GoodBadResponse(false, "User doesnt exist"), null, UserDoesntExistsException())

        val member = dbQuery {
            WorkSpaceMembersTable.select {
                WorkSpaceMembersTable.workSpaceId.eq(params.workSpaceId)
            }.map {
                workSpacesMembersMapper.fromRowToT(it)
            }
        }

        member.forEach {
            if(it?.uid == params.uid){
                return BaseResponse.BadResponse(
                    Response.GoodBadResponse(false, "Member already added"), null, UserAlreadyAddedToWorkSpaceException()
                )
            }
        }

        addMemberToWorkSpace(params)

        return BaseResponse.GoodResponse(
            Response.GoodBadResponse(true, "User invited"), null
        )
    }

    override suspend fun getUserWorkSpaces(userId: Int): BaseResponse {

        val workSpaces = mutableListOf<WorkSpace>()

        val userWorkSpaceInvited = dbQuery {
            WorkSpaceMembersTable.select {
                WorkSpaceMembersTable.uid.eq(userId)
            }.map {row->
                workSpacesMembersMapper.fromRowToT(row)
            }
        }
        for (workSpaceMember in userWorkSpaceInvited) {
            dbQuery {
                WorkSpaceTable.select {
                    WorkSpaceTable.workSpaceId.eq(workSpaceMember!!.workSpaceId)
                }.map {row->
                    WorkSpace(
                        workSpaceId = row[WorkSpaceTable.workSpaceId],
                        adminUId = row[WorkSpaceTable.adminUId],
                        name = row[WorkSpaceTable.name],
                        type = row[WorkSpaceTable.type],
                        description = row[WorkSpaceTable.type],
                        workSpaceMembers = getWorkSpaceUsers(row[WorkSpaceTable.workSpaceId]),
                    )
                }.singleOrNull()
            }.let {
                workSpaces.add(it!!)
            }
        }

        return BaseResponse.GoodResponse(
            Response.ListWorkspaceResponse(workSpaces)
        )

    }

    private fun getWorkSpaceUsers(workSpaceId: String): List<Member?>{
        val users = mutableListOf<Member>()
        val workspaceMembers = transaction{
            WorkSpaceMembersTable.select {
                WorkSpaceMembersTable.workSpaceId.eq(workSpaceId)
            }.map {row->
                workSpacesMembersMapper.fromRowToT(row)
            }
        }
        for (workspaceMember in workspaceMembers) {
            transaction {
                User.select{
                    User.uid.eq(workspaceMember!!.uid)
                }.map {
                    memberMapper.memberStatus = workspaceMember!!.status
                    memberMapper.workSpaceId = workspaceMember.workSpaceId
                    memberMapper.fromRowToT(it)
                }.singleOrNull()
            }.let {
                users.add(it!!)
            }
        }
        return users
    }

    override suspend fun getWorkSpace(workSpaceId: String?): BaseResponse {
        val workSpace = dbQuery {
            WorkSpaceTable.select {
                WorkSpaceTable.workSpaceId.eq(workSpaceId!!)
            }.map {row->
                WorkSpace(
                    workSpaceId = row[WorkSpaceTable.workSpaceId],
                    adminUId = row[WorkSpaceTable.adminUId],
                    name = row[WorkSpaceTable.name],
                    type = row[WorkSpaceTable.type],
                    description = row[WorkSpaceTable.type],
                    workSpaceMembers = getWorkSpaceUsers(row[WorkSpaceTable.workSpaceId]),
                )
            }
        }

       return BaseResponse.GoodResponse(
            Response.ListWorkspaceResponse(workSpace)
        )

    }


    private suspend fun addMemberToWorkSpace(params: WorkSpaceMember){
        dbQuery {
            WorkSpaceMembersTable.insert {
                it[WorkSpaceMembersTable.workSpaceId] = params.workSpaceId
                it[WorkSpaceMembersTable.uid] = params.uid
                it[WorkSpaceMembersTable.memberStatus] = params.status
            }
        }
    }


}
