package com.example.financeapp.core.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.financeapp.core.data.models.*

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): LiveData<List<Expense>>

    @Query("SELECT * FROM expenses ORDER BY date DESC LIMIT 10")
    fun getRecentExpenses(): LiveData<List<Expense>>

    @Query("SELECT SUM(amount) FROM expenses WHERE strftime('%Y-%m', date / 1000, 'unixepoch') = strftime('%Y-%m', 'now')")
    fun getCurrentMonthTotal(): LiveData<Double>

    @Query("SELECT category, SUM(amount) as total FROM expenses WHERE strftime('%Y-%m', date / 1000, 'unixepoch') = strftime('%Y-%m', 'now') GROUP BY category")
    fun getCurrentMonthSpendingByCategory(): LiveData<List<CategoryTotal>>

    @Query("SELECT category, SUM(amount) as total FROM expenses GROUP BY category")
    fun getTotalSpendingByCategory(): LiveData<List<CategoryTotal>>

    @Query("SELECT strftime('%Y-%m', date / 1000, 'unixepoch') as month, SUM(amount) as total FROM expenses GROUP BY month ORDER BY month")
    fun getMonthlySpending(): LiveData<List<MonthlyTotal>>

    @Query("SELECT * FROM expenses WHERE category = :category ORDER BY date DESC")
    fun getExpensesByCategory(category: String): LiveData<List<Expense>>

    @Insert
    suspend fun insert(expense: Expense)

    @Update
    suspend fun update(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)
}