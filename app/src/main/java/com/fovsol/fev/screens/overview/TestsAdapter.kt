package com.fovsol.fev.screens.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fovsol.fev.database.entities.Tests
import com.fovsol.fev.databinding.TestListItemViewBinding

class TestsAdapter(val clickListener: TestsListener) :
    ListAdapter<Tests, TestsAdapter.TestViewHolder>(TestsDiffCallback()) {

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        return TestViewHolder.from(parent)
    }

    class TestViewHolder private constructor(val binding: TestListItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Tests, clickListener: TestsListener) {
            binding.test = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TestViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TestListItemViewBinding.inflate(layoutInflater, parent, false)
                return TestViewHolder(binding)
            }
        }
    }
}

class TestsDiffCallback : DiffUtil.ItemCallback<Tests>() {
    override fun areItemsTheSame(oldItem: Tests, newItem: Tests): Boolean {
        return oldItem.test_id == newItem.test_id
    }

    override fun areContentsTheSame(oldItem: Tests, newItem: Tests): Boolean {
        return oldItem == newItem
    }
}

class TestsListener(val clickListener: (testId: Int) -> Unit) {
    fun onClick(test: Tests) = clickListener(test.test_id)
}