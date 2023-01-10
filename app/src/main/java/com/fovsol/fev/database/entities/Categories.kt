package com.fovsol.fev.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Categories")
data class Categories(
    @PrimaryKey
    val category_id: Int,

    @ColumnInfo(name = "category")
    val category: String
)