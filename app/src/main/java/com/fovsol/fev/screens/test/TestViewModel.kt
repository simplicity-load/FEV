package com.fovsol.fev.screens.test

import android.app.Application
import androidx.lifecycle.*
import com.fovsol.fev.combine
import com.fovsol.fev.database.TestDao
import com.fovsol.fev.database.entities.TestRelation
import kotlinx.coroutines.launch

class TestViewModel(
    val database: TestDao, application: Application, val testId: Int
) : AndroidViewModel(application) {

    private val _navigateToResult = MutableLiveData<Boolean>()
    val navigateToResult: LiveData<Boolean>
        get() = _navigateToResult

    private val counter = MutableLiveData(0)

    val progressString = Transformations.map(counter) {
        counter.value.let { v -> "${v?.inc()}/30" }
    }

    private var questionList = database.getQuestionsByTest(testId)

    var image = Transformations.map(questionList.combine(counter)) {
        it.first?.get(it.second!!)?.questions?.image
    }
    var question = Transformations.map(questionList.combine(counter)) {
        it.first?.get(it.second!!)?.questions?.question
    }
    var answer1 = Transformations.map(questionList.combine(counter)) {
        it.first?.get(it.second!!)?.questions?.answer_1
    }
    var answer2 = Transformations.map(questionList.combine(counter)) {
        it.first?.get(it.second!!)?.questions?.answer_2
    }
    var answer3 = Transformations.map(questionList.combine(counter)) {
        it.first?.get(it.second!!)?.questions?.answer_3
    }

    private var selectList = MutableList(30) { Selected() }

    var currentSelection = MutableLiveData(selectList[counter.value!!])

    fun nextQuestion() = when (counter.value) {
        in 0..28 -> {
            counter.value = counter.value?.inc()
            currentSelection.value = selectList[counter.value!!]
        }
        29 -> {
            calculateCorrectAnswers()
            _navigateToResult.value = true
        }
        else -> {} // null
    }

    fun prevQuestion() = when (counter.value) {
        in 1..29 -> {
            counter.value = counter.value?.dec()
            currentSelection.value = selectList[counter.value!!]
        }
        else -> {} // null
    }

    val testRelationBuilder =
        MutableList<TestRelation>(30) { TestRelation(0, testId, 0, 0, 0, 0, 0) }

    private fun calculateCorrectAnswers() {
        val questions = questionList.value
        if (questions != null) {
            for (i in 0..29) {
                testRelationBuilder[i].id = questions[i].id
                testRelationBuilder[i].question_id = questions[i].questions.question_id
                testRelationBuilder[i].correct =
                    if (questions[i].questions.answer_1_cor.toBoolean() == selectList[i].ans1_sel &&
                        questions[i].questions.answer_2_cor.toBoolean() == selectList[i].ans2_sel &&
                        questions[i].questions.answer_3_cor.toBoolean() == selectList[i].ans3_sel
                    ) true.toInt() else false.toInt()
                testRelationBuilder[i].ans_1_sel = selectList[i].ans1_sel.toInt()
                testRelationBuilder[i].ans_2_sel = selectList[i].ans2_sel.toInt()
                testRelationBuilder[i].ans_3_sel = selectList[i].ans3_sel.toInt()
            }
        }
        callDB()
    }

    private fun callDB() {
        viewModelScope.launch {
            pushAnswers()
        }
    }

    private suspend fun pushAnswers() {
        testRelationBuilder.forEach {
            database.updateTestRelation(it.id, it.correct ?: 0, it.ans_1_sel ?: 0, it.ans_2_sel ?: 0, it.ans_3_sel ?: 0)
        }
    }

    private fun Int.toBoolean() = this == 1
    private fun Boolean.toInt() = if (this) 1 else 0

    fun doneNavigating() {
        _navigateToResult.value = false
    }
}

data class Selected(
    var ans1_sel: Boolean = false,
    var ans2_sel: Boolean = false,
    var ans3_sel: Boolean = false
)