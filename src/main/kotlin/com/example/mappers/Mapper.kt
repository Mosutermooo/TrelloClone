package com.example.mappers

import org.jetbrains.exposed.sql.ResultRow

interface Mapper<T> {
    fun fromRowToT(row: ResultRow?) : T
}