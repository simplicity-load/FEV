package com.fovsol.fev.screens.result

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.fovsol.fev.database.entities.TestRelation
import com.fovsol.fev.setPercTextColor

@BindingAdapter("questionResultText")
fun TextView.setQuestionResultText(item: TestRelation) {
    text = (item.id - (item.test_id * 30)+30).toString()
    val newColor = setPercTextColor(if (item.correct == 1) 30 else 0, context)
    setTextColor(newColor)
}