package com.example.database.tables

import com.example.database.tables.User.autoIncrement
import com.example.models.user.User
import org.jetbrains.exposed.sql.Table

object WorkSpaceTable : Table("work_spaces") {
    val id = integer("id").autoIncrement()
    val workSpaceId = varchar("work_space_id", 200)
    val adminUId = integer("adminId")
    val name = varchar("name", 200)
    val type = varchar("type", 200)
    val description = varchar("description", 200)
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}


