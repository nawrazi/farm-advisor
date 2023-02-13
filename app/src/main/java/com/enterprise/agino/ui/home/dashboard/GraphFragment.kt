package com.enterprise.agino.ui.home.dashboard

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.component1
import androidx.core.util.component2
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.enterprise.agino.R
import com.enterprise.agino.databinding.FragmentGraphScreenBinding
import com.enterprise.agino.domain.model.Sensor
import com.enterprise.agino.utils.millisToDateString
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.datepicker.*
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class GraphFragment : Fragment() {
    private var _binding: FragmentGraphScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GraphViewModel by viewModels()
    private lateinit var adapter: SensorsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGraphScreenBinding.inflate(inflater, container, false)

        binding.dateInput.apply {
            inputType = InputType.TYPE_NULL
            setOnClickListener { showDatePicker(this) }
        }

        viewModel.temperatureGraphDataSet.observe(viewLifecycleOwner) {
            binding.chart1.apply {
                data = LineData(it)
                it.setColors(
                    resources.getColor(R.color.royal_orange, null)
                )
                data.setDrawValues(false)
                it.setDrawCircles(false)
                it.lineWidth = 3f
                it.mode = LineDataSet.Mode.CUBIC_BEZIER
                styleLineGraph(this)
                invalidate()
            }
        }

        viewModel.precipitationGraphDataSet.observe(viewLifecycleOwner) {
            binding.chart2.apply {
                val snowDepth = it.component1()
                val missingData = it.component2()
                snowDepth.color = resources.getColor(R.color.navy_blue, null)
                missingData.color = resources.getColor(R.color.light_grey, null)
                data = BarData(listOf(snowDepth, missingData))

                data.setDrawValues(false)
                styleBarGraph(this)
                invalidate()
            }
        }

        setupSensorAdapter()
        return binding.root
    }

    private fun setupSensorAdapter() {
        adapter = SensorsAdapter()
        binding.sensorsList.adapter = adapter

        val data = listOf(
            Sensor(
                "45678987654", null, "", 0, 97,
                "", "", 0.0, 0.0, 0, ""
            ),
            Sensor(
                "98765445678", null, "", 0, 87,
                "", "", 0.0, 0.0, 0, ""
            ),
            Sensor(
                "76544898567", null, "", 0, 57,
                "", "", 0.0, 0.0, 0, ""
            ),
            Sensor(
                "67898575446", null, "", 0, 77,
                "", "", 0.0, 0.0, 0, ""
            )
        )

        adapter.setItems(data)
    }

    private fun styleBarGraph(chart: BarChart) {
        // add the name of the days on top of x-axis
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(viewModel.days)

        chart.apply {
            xAxis.setDrawGridLines(false)
            xAxis.textSize = 15f
            extraTopOffset = 20f
            extraBottomOffset = 20f
            axisLeft.axisLineColor = resources.getColor(R.color.light_grey, null)
            xAxis.setDrawAxisLine(false)
            axisLeft.setDrawAxisLine(false)
            axisRight.isEnabled = false
            axisLeft.textColor = resources.getColor(android.R.color.darker_gray, null)
            description.isEnabled = false
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.form = Legend.LegendForm.CIRCLE
            xAxis.granularity = 1f  // set the minimum distance between labels
            legend.yOffset = 10f  // move the legend up a bit
            setFitBars(true)
            setVisibleXRangeMaximum(7f)

        }
    }

    private fun styleLineGraph(chart: LineChart) {
        // add the name of the days on top of x-axis
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(viewModel.days)

        chart.apply {
            xAxis.setDrawGridLines(false)
            xAxis.textSize = 15f
            extraTopOffset = 20f
            extraBottomOffset = 20f
            axisLeft.axisLineColor = resources.getColor(R.color.light_grey, null)
            xAxis.setDrawAxisLine(false)
            axisLeft.setDrawAxisLine(false)
            axisRight.isEnabled = false
            axisLeft.textColor = resources.getColor(android.R.color.darker_gray, null)
            description.isEnabled = false
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.form = Legend.LegendForm.CIRCLE
            xAxis.granularity = 1f  // set the minimum distance between labels
            legend.yOffset = 10f  // move the legend up a bit
            setVisibleXRangeMaximum(7f)

        }
    }

    private fun showDatePicker(editText: TextInputEditText) {
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(
                    CompositeDateValidator.allOf(
                        listOf(
                            DateValidatorPointForward.now(),
                            DateValidatorPointBackward.before(
                                Calendar.getInstance().also {
                                    it.add(Calendar.MONTH, 3)
                                }.timeInMillis
                            )
                        )
                    )
                )
                .build()

        val datePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setCalendarConstraints(constraintsBuilder)
            .build()

        datePicker.addOnPositiveButtonClickListener {
            val (startDate, endDate) = it
            editText.setText(
                resources.getString(
                    R.string.date_range, millisToDateString(startDate), millisToDateString(endDate)
                )
            )
        }

        datePicker.show(requireActivity().supportFragmentManager, null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}