package com.enterprise.agino.ui.home.dashboard

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.util.component1
import androidx.core.util.component2
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.enterprise.agino.R
import com.enterprise.agino.common.Resource
import com.enterprise.agino.databinding.FragmentGraphScreenBinding
import com.enterprise.agino.utils.gone
import com.enterprise.agino.utils.millisToDateString
import com.enterprise.agino.utils.show
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
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class GraphFragment : Fragment(), SensorsAdapter.OnSensorOptionsClickListener {
    private var _binding: FragmentGraphScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GraphViewModel by viewModels()
    private val navArgs: GraphFragmentArgs by navArgs()
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

        binding.addSensorButton.setOnClickListener {
            findNavController().navigate(
                GraphFragmentDirections.actionGraphFragmentToAddNewSensorFragment(
                    navArgs.fieldId
                )
            )
        }

        binding.backButtonField.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.viewModelScope.launch {
            viewModel.graphData.observe(viewLifecycleOwner) {
                viewModel.populateGraphData()
            }
        }


        adapter =
            SensorsAdapter(object : SensorsAdapter.OnSensorOptionsClickListener {
                override fun onSensorOptionsClick(holder: SensorsAdapter.ViewHolder) {
                    val popup = PopupMenu(requireContext(), holder.options)
                    popup.menuInflater.inflate(R.menu.sensor_popup_menu, popup.menu)
                    popup.show()
                }
            })

        binding.sensorsList.adapter = adapter
        binding.sensorsList.layoutManager = GridLayoutManager(requireContext(), 2)

        setupFieldPopupOptionsListener()
        setupTemperatureObserver()
        setupPrecipitationObserver()
        setupSnowDepthObserver()
        setupWindObserver()
        setupSensorsList()

        return binding.root
    }

    private fun setupFieldPopupOptionsListener() {
        binding.optionsField.setOnClickListener {
            val popup = PopupMenu(requireContext(), it)
            popup.menuInflater.inflate(R.menu.field_popup_menu, popup.menu)
            popup.show()
        }
    }

    private fun setupTemperatureObserver() {
        viewModel.temperatureGraphDataSet.observe(viewLifecycleOwner) {
            binding.temperatureChart.apply {
                data = LineData(it)
                it.setColors(
                    resources.getColor(R.color.royal_orange, null)
                )
                data.setDrawValues(false)
                it.setDrawCircles(false)
                it.lineWidth = 3f
                it.mode = LineDataSet.Mode.CUBIC_BEZIER
                styleLineGraph(this, viewModel.temperatureDays)
                invalidate()
            }
        }
    }

    private fun setupPrecipitationObserver() {
        viewModel.precipitationGraphDataSet.observe(viewLifecycleOwner) {
            binding.precipitationChart.apply {
                data = BarData(it)
                it.color = resources.getColor(R.color.navy_blue, null)
                data.setDrawValues(false)
                styleBarGraph(this, viewModel.precipitationDays)
                invalidate()
            }
        }
    }

    private fun setupWindObserver() {
        viewModel.windGraphDataSet.observe(viewLifecycleOwner) {
            binding.windChart.apply {
                val maxGust = it.component1()
                val avgWindSpeed = it.component2()
                maxGust.color = resources.getColor(R.color.royal_orange, null)
                avgWindSpeed.color = resources.getColor(R.color.forest_green, null)

                data = LineData(listOf(maxGust, avgWindSpeed))
                data.setDrawValues(false)
                maxGust.setDrawCircles(false)
                maxGust.lineWidth = 3f
                maxGust.mode = LineDataSet.Mode.CUBIC_BEZIER
                avgWindSpeed.setDrawCircles(false)
                avgWindSpeed.lineWidth = 3f
                avgWindSpeed.mode = LineDataSet.Mode.CUBIC_BEZIER
                styleLineGraph(this, viewModel.windDays)
                invalidate()
            }
        }
    }

    private fun setupSnowDepthObserver() {
        viewModel.snowDepthGraphDataSet.observe(viewLifecycleOwner) {
            binding.snowDepthChart.apply {
                val snowDepth = it.component1()
                val missingData = it.component2()
                snowDepth.color = resources.getColor(R.color.navy_blue, null)
                missingData.color = resources.getColor(R.color.light_grey, null)
                data = BarData(listOf(snowDepth, missingData))

                data.setDrawValues(false)
                styleBarGraph(this, viewModel.snowDepthDays)
                invalidate()
            }
        }
    }

    private fun styleBarGraph(chart: BarChart, days: List<String>) {
        // add the name of the days on top of x-axis
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(days)

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
//            setVisibleXRangeMaximum(7f)

        }
    }

    private fun styleLineGraph(chart: LineChart, days: List<String>) {
        // add the name of the days on top of x-axis
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(days)

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
//            setVisibleXRangeMaximum(7f)

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

    override fun onSensorOptionsClick(holder: SensorsAdapter.ViewHolder) {
        holder.options.setOnClickListener {
            val popup = PopupMenu(requireContext(), it)
            popup.menuInflater.inflate(R.menu.sensor_popup_menu, popup.menu)
            popup.show()
        }
    }

    private fun setupSensorsList() {
        viewModel.viewModelScope.launch {
            viewModel.getSensors(navArgs.fieldId).observe(viewLifecycleOwner) { sensors ->
                when (sensors) {
                    is Resource.Success -> {
                        if (sensors.value!!.isEmpty()) {
                            binding.noSensorsText.show()
                            binding.sensorProgressBar.gone()
                        } else {
                            binding.noSensorsText.gone()
                            binding.sensorProgressBar.gone()
                            adapter.setItems(sensors.value)
                        }
                    }
                    is Resource.Loading -> {
                        binding.noSensorsText.gone()
                        binding.sensorProgressBar.show()
                    }
                    else -> {
                        binding.noSensorsText.gone()
                        binding.sensorProgressBar.gone()
                    }
                }
            }
        }
    }

}