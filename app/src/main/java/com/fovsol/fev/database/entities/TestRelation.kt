package com.fovsol.fev.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TestRelation")
data class TestRelation(
    @PrimaryKey
    var id: Int,

    @ColumnInfo(name = "test_id")
    var test_id: Int,

    @ColumnInfo(name = "question_id")
    var question_id: Int,

    @ColumnInfo(name = "correct")
    var correct: Int?,

    @ColumnInfo(name = "ans_1_sel")
    var ans_1_sel: Int?,

    @ColumnInfo(name = "ans_2_sel")
    var ans_2_sel: Int?,

    @ColumnInfo(name = "ans_3_sel")
    var ans_3_sel: Int?
)