package com.example.financeapp.expense

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.financeapp.R
import com.example.financeapp.core.data.models.Expense
import java.text.SimpleDateFormat
import java.util.*

class ExpensesAdapter(private val onDeleteClicked: (Expense) -> Unit) :
    ListAdapter<Expense, ExpensesAdapter.ExpenseViewHolder>(ExpenseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view, onDeleteClicked)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ExpenseViewHolder(itemView: View, private val onDeleteClicked: (Expense) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val textCategory: TextView = itemView.findViewById(R.id.text_category)
        private val textAmount: TextView = itemView.findViewById(R.id.text_amount)
        private val textDate: TextView = itemView.findViewById(R.id.text_date)
        private val iconDelete: ImageView = itemView.findViewById(R.id.icon_delete)

        fun bind(expense: Expense) {
            textCategory.text = expense.category
            textAmount.text = "$${String.format("%.2f", expense.amount)}"
            textDate.text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(expense.date))
            iconDelete.setOnClickListener { onDeleteClicked(expense) }
        }
    }

    class ExpenseDiffCallback : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean = oldItem == newItem
    }
}