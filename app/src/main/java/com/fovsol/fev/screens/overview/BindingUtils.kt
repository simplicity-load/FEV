package com.fovsol.fev.screens.overview

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.fovsol.fev.database.entities.Tests
import com.fovsol.fev.setPercTextColor

@BindingAdapter("testTextFormatted")
fun TextView.setTestTextFormatted(item: Tests) {
    text = formatTestText(item.test_id, context.resources)
}

@BindingAdapter("percTextFormatted")
fun TextView.setPercTextFormatted(item: Tests) {
    text = formatPercText(item.correct_perc, context.resources)
    val newColor = setPercTextColor(item.correct_perc, context)
    setTextColor(newColor)
}
