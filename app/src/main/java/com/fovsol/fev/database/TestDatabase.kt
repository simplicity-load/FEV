package com.fovsol.fev.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fovsol.fev.database.entities.Categories
import com.fovsol.fev.database.entities.Questions
import com.fovsol.fev.database.entities.TestRelation
import com.fovsol.fev.database.entities.Tests

@Database(entities = [Questions::class, TestRelation::class, Categories::class, Tests::class], version = 1)
abstract class TestDatabase : RoomDatabase() {
    abstract val testDao: TestDao

    companion object {
        @Volatile
        private var INSTANCE: TestDatabase? = null

        fun getInstance(context: Context): TestDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TestDatabase::class.java,
                        "questions"
                    )
                        .createFromAsset("database/questions.db")
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}