package com.example.financeapp.budget

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.financeapp.databinding.ItemBudgetBinding

class BudgetAdapter : RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>() {

    private val items = mutableListOf<CategoryBudget>()

    fun submitList(newList: List<CategoryBudget>) {
        val diffCallback = BudgetDiffCallback(items, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun getBudgets(): List<CategoryBudget> = items.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        val binding = ItemBudgetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BudgetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class BudgetViewHolder(private val binding: ItemBudgetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var textWatcher: TextWatcher? = null

        fun bind(categoryBudget: CategoryBudget) {
            binding.textCategory.text = categoryBudget.category

            // Remove previous TextWatcher to avoid duplicate listeners
            textWatcher?.let { binding.editBudget.removeTextChangedListener(it) }

            // Prevent unnecessary setText calls that cause cursor jumping
            val currentText = binding.editBudget.text.toString()
            val newText = categoryBudget.budget.toString()
            if (currentText != newText) {
                binding.editBudget.setText(newText)
                binding.editBudget.setSelection(newText.length) // Preserve cursor position
            }

            textWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    val newBudget = s.toString().toDoubleOrNull() ?: 0.0
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION && items[position].budget != newBudget) {
                        items[position] = items[position].copy(budget = newBudget)
                    }
                }
            }
            binding.editBudget.addTextChangedListener(textWatcher!!)
        }
    }

    class BudgetDiffCallback(
        private val oldList: List<CategoryBudget>,
        private val newList: List<CategoryBudget>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
            return oldList[oldPos].category == newList[newPos].category
        }

        override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
            return oldList[oldPos] == newList[newPos]
        }
    }
}
