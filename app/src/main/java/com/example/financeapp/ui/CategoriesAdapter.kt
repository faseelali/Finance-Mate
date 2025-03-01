package com.example.financeapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.financeapp.R
import com.example.financeapp.home.BudgetStatus

class CategoriesAdapter(private val onCategoryClicked: (String) -> Unit) :
    ListAdapter<BudgetStatus, CategoriesAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view, onCategoryClicked)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CategoryViewHolder(itemView: View, private val onCategoryClicked: (String) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val textCategoryStatus: TextView = itemView.findViewById(R.id.text_category_status)

        fun bind(status: BudgetStatus) {
            val baseText = "${status.category}: $${String.format("%.2f", status.spending)} / $${status.budget}"
            textCategoryStatus.text = if (status.budget > 0 && status.spending >= status.budget * 0.8) {
                "$baseText (Warning)"
            } else {
                baseText
            }
            itemView.setOnClickListener { onCategoryClicked(status.category) }
        }
    }

    class CategoryDiffCallback : DiffUtil.ItemCallback<BudgetStatus>() {
        override fun areItemsTheSame(oldItem: BudgetStatus, newItem: BudgetStatus): Boolean =
            oldItem.category == newItem.category
        override fun areContentsTheSame(oldItem: BudgetStatus, newItem: BudgetStatus): Boolean =
            oldItem == newItem
    }
}