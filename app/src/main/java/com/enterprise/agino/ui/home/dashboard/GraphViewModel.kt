package com.enterprise.agino.ui.home.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enterprise.agino.R
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet

class GraphViewModel() : ViewModel() {

    // temperature graph dataset
    private val temperatureEntries = mutableListOf<Entry>()
    val _tempratureGraphDataSet =
        MutableLiveData(LineDataSet(temperatureEntries, "Measured Temperature"))
    val temperatureGraphDataSet: LiveData<LineDataSet> = _tempratureGraphDataSet

    // precipitation graph dataset
    private val precipitationEntriesSnow = mutableListOf<BarEntry>()
    private val precipitationEntriesMissing = mutableListOf<BarEntry>()
    val _precipitationGraphDataSet = MutableLiveData(
        Pair(
            BarDataSet(precipitationEntriesSnow, "Snow Depth"),
            BarDataSet(precipitationEntriesMissing, "Missing Data")
        )
    )
    val precipitationGraphDataSet: LiveData<Pair<BarDataSet, BarDataSet>> =
        _precipitationGraphDataSet
    val days = mutableListOf<String>()
    val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    init {
        setupPrecipitationGraphData()
        setupTemperatureGraphData()
    }

    private fun setupPrecipitationGraphData() {

        // create a dummy list of entries for each day of the week
        var odd = true
        for (i in 0..100) {
            days.add(daysOfWeek[i % 7])
            if (odd) {
                precipitationEntriesSnow.add(
                    BarEntry(i.toFloat(), (Math.random() * 100).toFloat(), R.drawable.ic_add)
                )
                odd = false
            } else {
                precipitationEntriesMissing.add(
                    BarEntry(i.toFloat(), (Math.random() * 100).toFloat(), R.drawable.ic_add)
                )
                odd = true
            }
        }

        _precipitationGraphDataSet.value = Pair(
            BarDataSet(precipitationEntriesSnow, "Snow Depthz"),
            BarDataSet(precipitationEntriesMissing, "Missing Data")
        )
    }

    private fun setupTemperatureGraphData() {

        for (i in 0..100) {
            days.add(daysOfWeek[i % 7])
            temperatureEntries.add(
                Entry(i.toFloat(), (Math.random() * 100).toFloat(), R.drawable.ic_add)
            )
        }

        _tempratureGraphDataSet.value = LineDataSet(temperatureEntries, "Measured Temperature")
    }
}