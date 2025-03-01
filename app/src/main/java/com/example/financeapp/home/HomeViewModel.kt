package com.example.financeapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.core.data.models.Budget
import com.example.financeapp.core.data.models.CategoryTotal
import com.example.financeapp.core.data.models.Expense
import com.example.financeapp.core.data.repository.FinanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BudgetStatus(val category: String, val spending: Double, val budget: Double)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FinanceRepository
) : ViewModel() {
    val currentMonthTotal: LiveData<Double> = repository.currentMonthTotal
    val recentExpenses: LiveData<List<Expense>> = repository.recentExpenses
    val currentMonthSpendingByCategory: LiveData<List<CategoryTotal>> = repository.currentMonthSpendingByCategory
    val allBudgets: LiveData<List<Budget>> = repository.allBudgets

    private val _budgetStatus = MediatorLiveData<List<BudgetStatus>>()
    val budgetStatus: LiveData<List<BudgetStatus>> = _budgetStatus

    init {
        _budgetStatus.addSource(currentMonthSpendingByCategory) { spending ->
            val budgets = allBudgets.value ?: emptyList()
            val categories = repository.allCategories.value?.map { it.name } ?: emptyList()
            updateBudgetStatus(spending, budgets, categories)
        }
        _budgetStatus.addSource(allBudgets) { budgets ->
            val spending = currentMonthSpendingByCategory.value ?: emptyList()
            val categories = repository.allCategories.value?.map { it.name } ?: emptyList()
            updateBudgetStatus(spending, budgets, categories)
        }
        _budgetStatus.addSource(repository.allCategories) { categories ->
            val spending = currentMonthSpendingByCategory.value ?: emptyList()
            val budgets = allBudgets.value ?: emptyList()
            updateBudgetStatus(spending, budgets, categories.map { it.name })
        }
    }

    private fun updateBudgetStatus(spending: List<CategoryTotal>, budgets: List<Budget>, categories: List<String>) {
        val statusList = categories.map { category ->
            val totalSpending = spending.find { it.category == category }?.total ?: 0.0
            val budget = budgets.find { it.category == category }?.monthlyBudget ?: 0.0
            BudgetStatus(category, totalSpending, budget)
        }
        _budgetStatus.value = statusList // Set value on the MediatorLiveData instance
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            repository.deleteExpense(expense)
        }
    }
}