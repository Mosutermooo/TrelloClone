package com.example.mappers

import com.example.database.tables.User
import com.example.database.tables.WorkSpaceMembersTable
import com.example.database.tables.WorkSpaceTable
import com.example.models.work_space.Member
import com.example.models.work_space.MemberStatus
import com.example.models.work_space.WorkSpace
import com.example.models.work_space.WorkSpaceMember
import org.jetbrains.exposed.sql.ResultRow

class WorkSpaceMapper : Mapper<WorkSpace?> {
    override fun fromRowToT(row: ResultRow?): WorkSpace? {
        return if(row == null) null
        else{
            WorkSpace(
                workSpaceId = row[WorkSpaceTable.workSpaceId],
                adminUId = row[WorkSpaceTable.adminUId],
                name = row[WorkSpaceTable.name],
                type = row[WorkSpaceTable.type],
                description = row[WorkSpaceTable.type],
                workSpaceMembers = emptyList(),
            )
        }
    }
}

class WorkSpacesMembersMapper : Mapper<WorkSpaceMember?>{
    override fun fromRowToT(row: ResultRow?): WorkSpaceMember? {
        return if(row == null) null
        else{
            WorkSpaceMember(
                workSpaceId = row[WorkSpaceMembersTable.workSpaceId],
                uid = row[WorkSpaceMembersTable.uid],
                status = row[WorkSpaceMembersTable.memberStatus]
            )
        }
    }
}

class MemberMapper : Mapper<Member?> {

    var memberStatus: String = ""
    var workSpaceId: String = ""

    override fun fromRowToT(row: ResultRow?): Member? {
        return if(row == null) null
        else {
            Member(
                uid = row[com.example.database.tables.User.uid],
                name = row[com.example.database.tables.User.name],
                lastname = row[com.example.database.tables.User.lastname],
                tag = row[com.example.database.tables.User.tag],
                email = row[com.example.database.tables.User.email],
                phoneNumber = row[com.example.database.tables.User.phoneNumber],
                memberStatus, workSpaceId
            )
        }
    }

}