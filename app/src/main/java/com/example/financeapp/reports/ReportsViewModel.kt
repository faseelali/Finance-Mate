package com.example.financeapp.reports

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.financeapp.core.data.models.CategoryTotal
import com.example.financeapp.core.data.models.MonthlyTotal
import com.example.financeapp.core.data.repository.FinanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val repository: FinanceRepository
) : ViewModel() {
    val totalSpendingByCategory: LiveData<List<CategoryTotal>> = repository.totalSpendingByCategory
    val monthlySpending: LiveData<List<MonthlyTotal>> = repository.monthlySpending
}