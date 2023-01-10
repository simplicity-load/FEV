package com.fovsol.fev.screens.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fovsol.fev.database.entities.TestRelation
import com.fovsol.fev.databinding.ResultListItemViewBinding


class ResultAdapter :
    ListAdapter<TestRelation, ResultAdapter.ResultViewHolder>(ResultDiffCallback()) {

    override fun onBindViewHolder(holder: ResultAdapter.ResultViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultAdapter.ResultViewHolder {
        return ResultAdapter.ResultViewHolder.from(parent)
    }

    class ResultViewHolder private constructor(val binding: ResultListItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TestRelation) {
            binding.testRelation = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ResultAdapter.ResultViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ResultListItemViewBinding.inflate(layoutInflater, parent, false)
                return ResultAdapter.ResultViewHolder(binding)
            }
        }
    }
}

class ResultDiffCallback : DiffUtil.ItemCallback<TestRelation>() {
    override fun areItemsTheSame(oldItem: TestRelation, newItem: TestRelation): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TestRelation, newItem: TestRelation): Boolean {
        return oldItem == newItem
    }
}

//class TestsListener(val clickListener: (testId: Int) -> Unit) {
//    fun onClick(test: Tests) = clickListener(test.test_id)
//}