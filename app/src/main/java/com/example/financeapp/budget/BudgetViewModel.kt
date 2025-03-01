package com.example.financeapp.budget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.core.data.models.Budget
import com.example.financeapp.core.data.models.Category
import com.example.financeapp.core.data.repository.FinanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CategoryBudget(val category: String, val budget: Double)

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val repository: FinanceRepository
) : ViewModel() {
    val allBudgets: LiveData<List<Budget>> = repository.allBudgets
    val allCategories: LiveData<List<Category>> = repository.allCategories

    private val _categoriesWithBudgets = MediatorLiveData<List<CategoryBudget>>()
    val categoriesWithBudgets: LiveData<List<CategoryBudget>> = _categoriesWithBudgets

    init {
        _categoriesWithBudgets.addSource(allCategories) { updateCategoriesWithBudgets() }
        _categoriesWithBudgets.addSource(allBudgets) { updateCategoriesWithBudgets() }
    }

    private fun updateCategoriesWithBudgets() {
        val categories = allCategories.value ?: emptyList()
        val budgets = allBudgets.value ?: emptyList()

        _categoriesWithBudgets.value = categories.map { category ->
            val budget = budgets.find { it.category == category.name }?.monthlyBudget ?: 0.0
            CategoryBudget(category.name, budget)
        }
    }

    fun insertBudget(budget: Budget) {
        viewModelScope.launch {
            repository.insertBudget(budget)
        }
    }

    fun deleteBudget(budget: Budget) {
        viewModelScope.launch {
            repository.deleteBudget(budget)
        }
    }

    fun insertCategory(category: Category) {
        viewModelScope.launch {
            repository.insertCategory(category)
        }
    }
}