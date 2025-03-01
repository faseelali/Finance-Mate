package com.example.financeapp.core.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.financeapp.core.data.models.Budget

@Dao
interface BudgetDao {
    @Query("SELECT * FROM budgets")
    fun getAllBudgets(): LiveData<List<Budget>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(budget: Budget)

    @Delete
    suspend fun delete(budget: Budget)
}