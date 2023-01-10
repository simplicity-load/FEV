package com.fovsol.fev.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fovsol.fev.database.entities.Questions
import com.fovsol.fev.database.entities.TestRelation
import com.fovsol.fev.database.entities.Tests

@Dao
interface TestDao {
    @Query("SELECT * FROM Questions WHERE question_id=:id")
    suspend fun getQuestionById(id: Int): Questions?

    @Query("SELECT question FROM Questions WHERE question_id=:id")
    suspend fun getOneQuestionsById(id: Int): String?

    @Query("SELECT * FROM Tests")
    fun getAllTests(): LiveData<List<Tests>>

    // Overview Fragment related Queries

    // Get count of available tests
    @Query("SELECT COUNT(*) FROM Tests")
    fun getTestCount(): LiveData<Int>

    // Get count of empty tests
    @Query("SELECT COUNT(*) FROM Tests WHERE correct_perc IS NULL")
    fun getEmptyTestCount(): LiveData<Int>

    // Get count of completed tests
    @Query("SELECT COUNT(*) FROM (SELECT correct_perc FROM Tests WHERE correct_perc IS NOT NULL) WHERE correct_perc > 25")
    fun getCompletedTestCount(): LiveData<Int>

    // Get count of incorrect tests TODO change to <= 25
    @Query("SELECT COUNT(*) FROM (SELECT correct_perc FROM Tests WHERE correct_perc IS NOT NULL) WHERE correct_perc <= 25")
    fun getIncorrectTestCount(): LiveData<Int>


    // Test Fragment related Queries

    // Get Questions with test_id = :id
    @Query("SELECT TestRelation.id, Questions.* FROM TestRelation JOIN Questions ON TestRelation.question_id = Questions.question_id WHERE TestRelation.test_id = :id")
    fun getQuestionsByTest(id: Int): LiveData<List<ExQuestions>>

    // Update TestRelation table with new values
    @Query("UPDATE TestRelation SET correct = :cor, ans_1_sel = :a1, ans_2_sel = :a2, ans_3_sel = :a3 WHERE TestRelation.id = :id")
    fun updateTestRelation(id: Int, cor: Int, a1: Int, a2: Int, a3: Int)


    // Result Fragment related Queries

    @Query("SELECT * FROM TestRelation WHERE TestRelation.test_id = :id")
    fun getTestRelation(id: Int): LiveData<List<TestRelation>>

    @Query("UPDATE Tests SET correct_perc = :p1, incorrect_perc = :p2 WHERE Tests.test_id = :id")
    fun updateTest(id: Int, p1: Int?, p2: Int?)
}

@Entity
data class ExQuestions(
    @PrimaryKey
    val id: Int,
    @Embedded
    val questions: Questions
)