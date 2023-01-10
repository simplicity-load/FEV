package com.fovsol.fev.screens.result

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.fovsol.fev.database.TestDao
import com.fovsol.fev.database.entities.TestRelation

class ResultViewModel(
    val database: TestDao, application: Application, val testId: Int
) : AndroidViewModel(application) {

    val mainTest: LiveData<List<TestRelation>> = database.getTestRelation(testId)

    val percCorrect = Transformations.map(mainTest) { test ->
        var corrCount = 0
        test.forEach {
            if ( it.correct == 1) {
                corrCount++
            }
        }
        corrCount
    }

    val percIncorrect = Transformations.map(mainTest) { test ->
        var incorrCount = 0
        test.forEach {
            if ( it.correct == 0) {
                incorrCount++
            }
        }
        incorrCount
    }

    fun updateTest() {
        database.updateTest(testId, percCorrect.value, percIncorrect.value)
    }
}