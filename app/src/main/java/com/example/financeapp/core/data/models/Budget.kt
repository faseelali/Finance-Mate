package com.example.financeapp.core.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budgets")
data class Budget(
    @PrimaryKey val category: String,
    val monthlyBudget: Double
)