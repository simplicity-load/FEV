package com.fovsol.fev.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tests")
data class Tests(
    @PrimaryKey
    val test_id: Int,

    @ColumnInfo(name = "correct_perc")
    var correct_perc: Int?,

    @ColumnInfo(name = "incorrect_perc")
    var incorrect_perc: Int?,

    @ColumnInfo(name = "empty_perc")
    var empty_perc: Int?
)