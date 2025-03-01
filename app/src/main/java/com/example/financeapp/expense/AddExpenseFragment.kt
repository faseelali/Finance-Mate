package com.example.financeapp.expense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.financeapp.budget.BudgetViewModel
import com.example.financeapp.core.data.models.Expense
import com.example.financeapp.databinding.FragmentAddExpenseBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddExpenseFragment : Fragment() {
    private var _binding: FragmentAddExpenseBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ExpenseViewModel by viewModels()
    private val budgetViewModel: BudgetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        budgetViewModel.allCategories.observe(viewLifecycleOwner) { categories ->
            val categoryNames = categories.map { it.name }
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categoryNames
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            binding.spinnerCategory.adapter = adapter
        }

        binding.buttonSave.setOnClickListener {
            val category = binding.spinnerCategory.selectedItem?.toString() ?: "Miscellaneous"
            val amount = binding.editAmount.text.toString().toDoubleOrNull() ?: 0.0
            val notes = binding.editNotes.text.toString()
            val date = System.currentTimeMillis()

            val expense = Expense(
                category = category,
                amount = amount,
                date = date,
                notes = notes
            )
            viewModel.insertExpense(expense)
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}