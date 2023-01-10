package com.fovsol.fev.screens.test

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fovsol.fev.database.TestDao

class TestViewModelFactory(
    private val dataSource: TestDao,
    private val application: Application,
    private val testId: Int
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestViewModel::class.java)) {
            return TestViewModel(dataSource, application, testId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
