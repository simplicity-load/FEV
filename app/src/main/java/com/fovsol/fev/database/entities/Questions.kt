package com.fovsol.fev.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Questions")//, primaryKeys = ["questionId"])
data class Questions(
    @PrimaryKey
    val question_id: Int,

    @ColumnInfo(name = "category_id")
    val category_id: Int,

    @ColumnInfo(name = "question")
    val question: String,

    @ColumnInfo(name = "answer_1")
    val answer_1: String,

    @ColumnInfo(name = "answer_2")
    val answer_2: String,

    @ColumnInfo(name = "answer_3")
    val answer_3: String,

    @ColumnInfo(name = "ans_1_cor")
    var answer_1_cor: Int,

    @ColumnInfo(name = "ans_2_cor")
    var answer_2_cor: Int,

    @ColumnInfo(name = "ans_3_cor")
    var answer_3_cor: Int,

    @ColumnInfo(name = "image")
    val image: String?
)