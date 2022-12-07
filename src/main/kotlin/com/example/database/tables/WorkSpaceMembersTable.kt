package com.example.database.tables

import com.example.database.tables.User.autoIncrement
import org.jetbrains.exposed.sql.Table


object WorkSpaceMembersTable : Table("work_space_users") {
    val id = integer("id").autoIncrement()
    val workSpaceId = varchar("work_space_id", 200)
    val uid = integer("uid")
    val memberStatus = varchar("member_status", 200)
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}