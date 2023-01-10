package com.fovsol.fev

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class PairLiveData<A, B>(first: LiveData<A>, second: LiveData<B>) :
    MediatorLiveData<Pair<A?, B?>>() {
    init {
        addSource(first) { value = it to second.value }
        addSource(second) { value = first.value to it }
    }
}

class TripleLiveData<A, B, C>(first: LiveData<A>, second: LiveData<B>, third: LiveData<C>) :
    MediatorLiveData<Triple<A?, B?, C?>>() {
    init {
        addSource(first) { value = Triple(it, second.value, third.value) }
        addSource(second) { value = Triple(first.value, it, third.value) }
        addSource(third) { value = Triple(first.value, second.value, it) }
    }
}

fun <A, B> LiveData<A>.combine(other: LiveData<B>): PairLiveData<A, B> {
    return PairLiveData(this, other)
}

fun <A, B, C> LiveData<A>.combine(
    second: LiveData<B>,
    third: LiveData<C>
): TripleLiveData<A, B, C> {
    return TripleLiveData(this, second, third)
}


fun setPercTextColor(percNumber: Int?, context: Context): Int {
    return when (percNumber) {
        in 26..30 -> {
            ContextCompat.getColor(
                context,
                R.color.green
            )
        }
        in 0..25 -> {
            ContextCompat.getColor(
                context,
                R.color.red
            )
        }
        else -> {
            ContextCompat.getColor(
                context,
                android.R.color.tab_indicator_text
            )
        }
    }
}