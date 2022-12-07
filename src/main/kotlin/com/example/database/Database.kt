package com.example.database

import com.example.database.tables.*
import com.example.utils.Constants.dbUrl
import com.example.utils.Constants.driver
import com.example.utils.Constants.password
import com.example.utils.Constants.user
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object Database {

    fun connect(){
        Database.connect(
            url = dbUrl,
            driver = driver,
            user = user,
            password = password
        )

        transaction {
            SchemaUtils.create(User)
            SchemaUtils.create(WorkSpaceTable)
            SchemaUtils.create(WorkSpaceMembersTable)
            SchemaUtils.create(BoardTable)
            SchemaUtils.create(CardTable)
            SchemaUtils.create(TaskTable)
        }

    }

    suspend fun <T> dbQuery(block: () -> T): T = withContext(Dispatchers.IO){
        transaction {
            block()
        }
    }

}