package com.example.mappers

import com.example.models.user.User
import org.jetbrains.exposed.sql.ResultRow

class UserMapper: Mapper<User?> {
    override fun fromRowToT(row: ResultRow?): User? {
        return if(row == null) null
        else{
            User(
                uid = row[com.example.database.tables.User.uid],
                name = row[com.example.database.tables.User.name],
                lastname = row[com.example.database.tables.User.lastname],
                tag = row[com.example.database.tables.User.tag],
                email = row[com.example.database.tables.User.email],
                phoneNumber = row[com.example.database.tables.User.phoneNumber]
            )
        }
    }

}