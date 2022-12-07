package com.example.database.tables

import org.jetbrains.exposed.sql.Table

object User : Table("users") {

    val uid = integer("Uid").autoIncrement()
    val name = varchar("name", 200)
    val lastname = varchar("lastname", 200)
    val email = varchar("email", 200)
    val tag = varchar("tag", 200)
    val phoneNumber = varchar("phone_number", 200)
    override val primaryKey: PrimaryKey = PrimaryKey(uid)
}