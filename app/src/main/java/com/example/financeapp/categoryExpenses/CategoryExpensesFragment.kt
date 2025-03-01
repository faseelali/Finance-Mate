package com.example.financeapp.categoryExpenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financeapp.databinding.FragmentCategoryExpensesBinding
import com.example.financeapp.expense.ExpenseViewModel
import com.example.financeapp.expense.ExpensesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryExpensesFragment : Fragment() {
    private var _binding: FragmentCategoryExpensesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ExpenseViewModel by viewModels()
    private lateinit var expensesAdapter: ExpensesAdapter
    private val args: CategoryExpensesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryExpensesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Use a fallback if category is null
        val category = args.category ?: "Unknown Category"
        binding.textCategoryTitle.text = "Expenses for $category"

        expensesAdapter = ExpensesAdapter { expense ->
            viewModel.deleteExpense(expense)
        }
        binding.recyclerViewCategoryExpenses.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = expensesAdapter
        }

        viewModel.getExpensesByCategory(category).observe(viewLifecycleOwner) { expenses ->
            expensesAdapter.submitList(expenses)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}