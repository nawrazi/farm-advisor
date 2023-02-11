package com.enterprise.agino.ui.home.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.enterprise.agino.R
import com.enterprise.agino.databinding.FragmentGraphScreenBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class GraphFragment : Fragment() {
    private var _binding: FragmentGraphScreenBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGraphScreenBinding.inflate(inflater, container, false)

        // test the graph
        setData()
        return binding.root
    }

    private fun setData() {
        val entries = mutableListOf<BarEntry>()
        val days = mutableListOf<String>()
        val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

        // create a dummy list of entries for each day of the week
        for (i in 0..100) {
            days.add(daysOfWeek[i % 7])
            entries.add(BarEntry(i.toFloat(), (Math.random() * 100).toFloat()))
        }

        // a dataset for our bar chart
        // TODO: using string resources for the label
        val set1 = BarDataSet(entries, "Measured Precipitation")
        // create a bar for each day of the week
        set1.setDrawValues(false)  // remove the actual values being displayed on top of the bars

        // TODO: change the color of the bars based on the value of the bar
        set1.setColors(
            resources.getColor(R.color.navy_blue, null),
            resources.getColor(R.color.light_grey, null)
        )

        // add the name of the days on top of x-axis
        binding.chart1.xAxis.valueFormatter = IndexAxisValueFormatter(days)

        // create a data object with the data sets
        val data = BarData(set1)

        // remove the vertical lines from the grid
        binding.chart1.xAxis.setDrawGridLines(false)

        // aesthetics related changes
        binding.chart1.xAxis.textSize = 15f
        binding.chart1.extraTopOffset = 20f
        binding.chart1.extraBottomOffset = 20f
        binding.chart1.axisLeft.axisLineColor = resources.getColor(R.color.light_grey, null)
        binding.chart1.xAxis.setDrawAxisLine(false)
        binding.chart1.axisLeft.setDrawAxisLine(false)
        binding.chart1.axisRight.isEnabled = false
        binding.chart1.axisLeft.textColor = resources.getColor(android.R.color.darker_gray, null)
        binding.chart1.description.isEnabled = false
        binding.chart1.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        binding.chart1.xAxis.granularity = 1f  // set the minimum distance between labels
        binding.chart1.legend.yOffset = 10f  // move the legend up a bit

        // set data
        binding.chart1.data = data
        binding.chart1.setVisibleXRangeMaximum(7f)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}