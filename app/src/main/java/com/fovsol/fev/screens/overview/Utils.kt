package com.fovsol.fev.screens.overview

import android.content.res.Resources
import com.fovsol.fev.R


fun dividePair(a: Int?, b: Int?): Float? =
    if (a != 0 && a != null && b != null) b.toFloat() / a.toFloat() else null



fun formatTestText(testNumber: Int, res: Resources): String {
    return res.getString(R.string.test_list_item_test_naming, testNumber)
}

fun formatPercText(percNumber: Int?, res: Resources): String {
    val percentage = if (percNumber != null) ((percNumber.toFloat() / 30f) * 100f).toInt() else 0
    return res.getString(R.string.test_list_item_perc_format, percentage)
}