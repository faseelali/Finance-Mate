package com.example.financeapp.core.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.financeapp.core.data.models.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    fun getAllCategories(): LiveData<List<Category>>

    @Insert
    suspend fun insert(category: Category)

    @Query("DELETE FROM categories WHERE name = :categoryName")
    suspend fun delete(categoryName: String)
}