package com.fovsol.fev.screens.overview

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.fovsol.fev.combine
import com.fovsol.fev.database.TestDao
import com.fovsol.fev.database.entities.Questions
import com.fovsol.fev.database.entities.Tests

class OverviewViewModel(
    val database: TestDao, application: Application
) : AndroidViewModel(application) {
//    private var question = MutableLiveData<Questions?>()
//    val loveQuestion = MutableLiveData<String>()

//    val exampleQuestion = MutableLiveData<String>()
//    private val tests: LiveData<List<Questions>> = database.getQuestionsByTest(1)

    val allTests: LiveData<List<Tests>> = database.getAllTests()

    //    val allTestsString: LiveData<String> = Transformations.map(allTests) {
//        formatTests(it)
//    }
    val testCount = Transformations.map(allTests) {
        countTests(it)
    }
    val emptyTestCount = database.getEmptyTestCount()
    val correctTestCount = database.getCompletedTestCount()
    val incorrectTestCount = database.getIncorrectTestCount()
//        Transformations.map(testCount.combine(emptyTestCount, correctTestCount)) {
//            Log.i("icTest", "${it.first} : ${it.second} : ${it.third}")
//            if (it.first != null && it.second != null && it.third != null) {
//                Log.i("icTest", "in")
//                it.first!! - (it.second!! + it.third!!)
//            } else {
//                0
//            }
//        }

    fun countTests(tests: List<Tests>): Int {
        return tests.size
    }

    val emptyPerc: LiveData<Float?> = Transformations.map(testCount.combine(emptyTestCount)) {
        Log.i("ePerc", "${it.first} : ${it.second} -> ${dividePair(it.first, it.second)}")
        dividePair(it.first, it.second)
    }
    val correctPerc: LiveData<Float?> = Transformations.map(testCount.combine(correctTestCount)) {
        Log.i("cPerc", "${it.first} : ${it.second} -> ${dividePair(it.first, it.second)}")
        dividePair(it.first, it.second)
    }
    val incorrectPerc: LiveData<Float?> =
        Transformations.map(testCount.combine(incorrectTestCount)) {
            Log.i("icPerc", "${it.first} : ${it.second} -> ${dividePair(it.first, it.second)}")
            dividePair(it.first, it.second)
        }

    val correctString: LiveData<String> =
        Transformations.map(emptyPerc.combine(correctPerc, incorrectPerc)) {
            "${it.first} empty\n${it.second} correct\n${it.third} incorrect"
        }
//    val correctPerc: Float
//        get() = if (testCount.value != 0) correctTestCount.value!!.toFloat() / testCount.value!!.toFloat() else 0.0f
//    val incorrectPerc: Float
//        get() = if (testCount.value != 0) incorrectTestCount.toFloat() / testCount.value!!.toFloat() else 0.0f

//    val testString: LiveData<String> = Transformations.map(tests) {
//        formatQuestions(it)
//    }

    private fun formatTests(tests: List<Tests>): String {
        val sb = StringBuilder()
        sb.apply {
            tests.forEach {
                append("${it.test_id} : ${it.correct_perc}%, ${it.incorrect_perc}%\n")
            }
        }
        return sb.toString()
    }


    private fun formatQuestions(questions: List<Questions>): String {
        val sb = StringBuilder()
        sb.apply {
            questions.forEach {
                append("${it.question_id} \n ${it.question}\n a1: ${it.answer_1}\na2: ${it.answer_2}\na3: ${it.answer_3}\n\n")
            }
        }
        return sb.toString()
    }

//    init {
//        initQuestion()
//    }

    private fun percCal(a: Int, b: Int): Float {
        return if (b != 0) a.toFloat() / b.toFloat() else 0f
    }

//    private fun initQuestion() {
//        viewModelScope.launch {
//            question.value = getQuestionFromDatabase()
//            exampleQuestion.value = getOne()
//        }
//    }

//    private suspend fun getOne(): String {
//        return database.getOneQuestionsById(1012).toString()
//    }
//
//    private suspend fun getQuestionFromDatabase(): Questions? {
//        return database.getQuestionById(1000).also {
//            loveQuestion.value = it?.question.toString()
//        }
//    }
}