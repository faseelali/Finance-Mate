package com.example.financeapp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financeapp.R
import com.example.financeapp.databinding.FragmentHomeBinding
import com.example.financeapp.expense.ExpensesAdapter
import com.example.financeapp.ui.CategoriesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var expensesAdapter: ExpensesAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expensesAdapter = ExpensesAdapter { expense ->
            viewModel.deleteExpense(expense)
        }
        binding.recyclerViewRecentExpenses.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = expensesAdapter
        }

        categoriesAdapter = CategoriesAdapter { category ->
            val action = HomeFragmentDirections.actionHomeToCategoryExpenses(category)
            findNavController().navigate(action)
        }
        binding.recyclerViewCategories.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = categoriesAdapter
        }

        viewModel.currentMonthTotal.observe(viewLifecycleOwner) { total ->
            binding.textTotalSpending.text = "Total: $${String.format("%.2f", total ?: 0.0)}"
        }

        viewModel.recentExpenses.observe(viewLifecycleOwner) { expenses ->
            expensesAdapter.submitList(expenses)
        }

        viewModel.budgetStatus.observe(viewLifecycleOwner) { statusList ->
            categoriesAdapter.submitList(statusList)
        }

        binding.buttonAddExpense.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_addExpense)
        }
        binding.buttonSetBudget.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_budget)
        }
        binding.buttonViewReports.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_reports)
        }
        binding.buttonAddCategory.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_addCategory)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}