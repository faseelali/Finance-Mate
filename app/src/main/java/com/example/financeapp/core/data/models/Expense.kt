package com.example.financeapp.core.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String,
    val amount: Double,
    val date: Long,
    val notes: String,
    val receiptImageUri: String? = null
)