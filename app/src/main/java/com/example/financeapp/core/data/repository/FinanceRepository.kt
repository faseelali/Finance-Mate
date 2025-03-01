package com.example.financeapp.core.data.repository

import androidx.lifecycle.LiveData
import com.example.financeapp.core.data.dao.BudgetDao
import com.example.financeapp.core.data.dao.CategoryDao
import com.example.financeapp.core.data.dao.ExpenseDao
import com.example.financeapp.core.data.models.*
import javax.inject.Inject

class FinanceRepository @Inject constructor(
    private val expenseDao: ExpenseDao,
    private val budgetDao: BudgetDao,
    private val categoryDao: CategoryDao
) {
    // Expenses
    val allExpenses: LiveData<List<Expense>> = expenseDao.getAllExpenses()
    val recentExpenses: LiveData<List<Expense>> = expenseDao.getRecentExpenses()
    val currentMonthTotal: LiveData<Double> = expenseDao.getCurrentMonthTotal()
    val currentMonthSpendingByCategory: LiveData<List<CategoryTotal>> = expenseDao.getCurrentMonthSpendingByCategory()
    val totalSpendingByCategory: LiveData<List<CategoryTotal>> = expenseDao.getTotalSpendingByCategory()
    val monthlySpending: LiveData<List<MonthlyTotal>> = expenseDao.getMonthlySpending()

    suspend fun insertExpense(expense: Expense) = expenseDao.insert(expense)
    suspend fun updateExpense(expense: Expense) = expenseDao.update(expense)
    suspend fun deleteExpense(expense: Expense) = expenseDao.delete(expense)

    // Budgets
    val allBudgets: LiveData<List<Budget>> = budgetDao.getAllBudgets()
    suspend fun insertBudget(budget: Budget) = budgetDao.insert(budget)
    suspend fun deleteBudget(budget: Budget) = budgetDao.delete(budget)

    // Categories
    val allCategories: LiveData<List<Category>> = categoryDao.getAllCategories()
    suspend fun insertCategory(category: Category) = categoryDao.insert(category)
    suspend fun deleteCategory(categoryName: String) = categoryDao.delete(categoryName)

    // Expenses by category
    fun getExpensesByCategory(category: String): LiveData<List<Expense>> =
        expenseDao.getExpensesByCategory(category)
}