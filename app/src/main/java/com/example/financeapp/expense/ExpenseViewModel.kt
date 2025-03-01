package com.example.financeapp.expense

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.core.data.models.Expense
import com.example.financeapp.core.data.repository.FinanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val repository: FinanceRepository
) : ViewModel() {
    val allExpenses: LiveData<List<Expense>> = repository.allExpenses

    fun insertExpense(expense: Expense) {
        viewModelScope.launch {
            repository.insertExpense(expense)
        }
    }

    fun updateExpense(expense: Expense) {
        viewModelScope.launch {
            repository.updateExpense(expense)
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            repository.deleteExpense(expense)
        }
    }

    fun getExpensesByCategory(category: String): LiveData<List<Expense>> {
        return repository.getExpensesByCategory(category)
    }
}