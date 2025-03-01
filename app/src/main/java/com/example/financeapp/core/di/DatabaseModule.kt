package com.example.financeapp.core.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.financeapp.core.data.database.AppDatabase
import com.example.financeapp.core.data.dao.BudgetDao
import com.example.financeapp.core.data.dao.CategoryDao
import com.example.financeapp.core.data.dao.ExpenseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "finance_database"
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                AppDatabase.PREDEFINED_CATEGORIES.forEach { category ->
                    db.execSQL("INSERT OR IGNORE INTO categories (name) VALUES ('$category')")
                }
            }
        }).build()
    }

    @Provides
    fun provideExpenseDao(database: AppDatabase): ExpenseDao = database.expenseDao()

    @Provides
    fun provideBudgetDao(database: AppDatabase): BudgetDao = database.budgetDao()

    @Provides
    fun provideCategoryDao(database: AppDatabase): CategoryDao = database.categoryDao()
}