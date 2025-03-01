package com.example.financeapp.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.financeapp.core.data.dao.BudgetDao
import com.example.financeapp.core.data.dao.CategoryDao
import com.example.financeapp.core.data.dao.ExpenseDao
import com.example.financeapp.core.data.models.Budget
import com.example.financeapp.core.data.models.Category
import com.example.financeapp.core.data.models.Expense

@Database(entities = [Expense::class, Budget::class, Category::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun budgetDao(): BudgetDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        val PREDEFINED_CATEGORIES = listOf("Food", "Transport", "Entertainment", "Bills", "Miscellaneous")
    }
}