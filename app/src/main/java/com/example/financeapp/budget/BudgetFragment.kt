package com.example.financeapp.budget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financeapp.core.data.models.Budget
import com.example.financeapp.databinding.FragmentBudgetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BudgetFragment : Fragment() {
    private var _binding: FragmentBudgetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BudgetViewModel by viewModels()
    private lateinit var budgetAdapter: BudgetAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        budgetAdapter = BudgetAdapter()
        binding.recyclerViewBudgets.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = budgetAdapter
        }

        // Observe ViewModel data and submit initial list to adapter
        viewModel.categoriesWithBudgets.observe(viewLifecycleOwner) { budgets ->
            budgetAdapter.submitList(budgets)
        }

        binding.buttonSaveBudgets.setOnClickListener {
            // Save all budgets from the adapter's current list
            budgetAdapter.getBudgets().forEach { categoryBudget ->
                if (categoryBudget.budget > 0) {
                    viewModel.insertBudget(Budget(categoryBudget.category, categoryBudget.budget))
                }
            }
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}