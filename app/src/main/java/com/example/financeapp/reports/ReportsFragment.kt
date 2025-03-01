package com.example.financeapp.reports

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.financeapp.databinding.FragmentReportsBinding
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportsFragment : Fragment() {
    private var _binding: FragmentReportsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReportsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.totalSpendingByCategory.observe(viewLifecycleOwner) { spending ->
            val entries = spending.map { PieEntry(it.total.toFloat(), it.category) }
            val dataSet = PieDataSet(entries, "Spending by Category").apply {
                colors = listOf(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA)
            }
            binding.pieChart.data = PieData(dataSet)
            binding.pieChart.description.isEnabled = false
            binding.pieChart.setUsePercentValues(true)
            binding.pieChart.invalidate()
        }

        viewModel.monthlySpending.observe(viewLifecycleOwner) { monthly ->
            val entries = monthly.mapIndexed { index, monthlyTotal ->
                Entry(index.toFloat(), monthlyTotal.total.toFloat())
            }
            val dataSet = LineDataSet(entries, "Monthly Spending").apply {
                color = Color.BLUE
                setDrawValues(false)
            }
            binding.lineChart.data = LineData(dataSet)
            binding.lineChart.description.isEnabled = false
            binding.lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(monthly.map { it.month })
            binding.lineChart.invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}