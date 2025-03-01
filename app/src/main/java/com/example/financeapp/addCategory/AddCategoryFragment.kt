package com.example.financeapp.addCategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.financeapp.budget.BudgetViewModel
import com.example.financeapp.core.data.models.Category
import com.example.financeapp.databinding.FragmentAddCategoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCategoryFragment : Fragment() {
    private var _binding: FragmentAddCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BudgetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSaveCategory.setOnClickListener {
            val categoryName = binding.editCategoryName.text.toString().trim()
            if (categoryName.isNotEmpty()) {
                viewModel.insertCategory(Category(categoryName))
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}