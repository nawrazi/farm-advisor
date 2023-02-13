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
    private val _tempratureGraphDataSet =
        MutableLiveData(LineDataSet(temperatureEntries, "Measured Temperature"))
    val temperatureGraphDataSet: LiveData<LineDataSet> = _tempratureGraphDataSet


    // precipitation graph dataset
    private val precipitationEntries = mutableListOf<BarEntry>()
    private val _precipitationGraphDataSet =
        MutableLiveData(BarDataSet(precipitationEntries, "Precipitation"))
    val precipitationGraphDataSet: LiveData<BarDataSet> = _precipitationGraphDataSet


    // snow depth graph dataset
    private val snowDepthEntriesSnow = mutableListOf<BarEntry>()
    private val snowDepthEntriesMissing = mutableListOf<BarEntry>()
    private val _snowDepthGraphDataSet = MutableLiveData(
        Pair(
            BarDataSet(snowDepthEntriesSnow, "Snow Depth"),
            BarDataSet(snowDepthEntriesMissing, "Missing Data")
        )
    )
    val snowDepthGraphDataSet: LiveData<Pair<BarDataSet, BarDataSet>> =
        _snowDepthGraphDataSet


    // wind graph dataset
    private val windEntriesMaxGust = mutableListOf<Entry>()
    private val windEntriesAvgWindSpeed = mutableListOf<Entry>()
    private val _windDepthGraphDataSet = MutableLiveData(
        Pair(
            LineDataSet(windEntriesMaxGust, "Max Gust"),
            LineDataSet(windEntriesAvgWindSpeed, "Average Wind Speed")
        )
    )
    val windGraphDataSet: LiveData<Pair<LineDataSet, LineDataSet>> = _windDepthGraphDataSet


    // common x-axis data
    val days = mutableListOf<String>()
    private val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    init {
        setupTemperatureGraphData()
        setupSnowDepthGraphData()
        setupPrecipitationGraphData()
        setupWindGraphData()
    }

    private fun setupSnowDepthGraphData() {

        // create a dummy list of entries for each day of the week
        var odd = true
        for (i in 0..100) {
            days.add(daysOfWeek[i % 7])
            if (odd) {
                snowDepthEntriesSnow.add(
                    BarEntry(i.toFloat(), (Math.random() * 100).toFloat(), R.drawable.ic_add)
                )
                odd = false
            } else {
                snowDepthEntriesMissing.add(
                    BarEntry(i.toFloat(), (Math.random() * 100).toFloat(), R.drawable.ic_add)
                )
                odd = true
            }
        }

        _snowDepthGraphDataSet.value = Pair(
            BarDataSet(snowDepthEntriesSnow, "Snow Depthz"),
            BarDataSet(snowDepthEntriesMissing, "Missing Data")
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

    private fun setupPrecipitationGraphData() {

        for (i in 0..100) {
            days.add(daysOfWeek[i % 7])
            precipitationEntries.add(
                BarEntry(i.toFloat(), (Math.random() * 100).toFloat(), R.drawable.ic_add)
            )
        }

        _precipitationGraphDataSet.value = BarDataSet(precipitationEntries, "Precipitation")
    }

    private fun setupWindGraphData() {
        for (i in 0..100) {
            days.add(daysOfWeek[i % 7])
            windEntriesMaxGust.add(
                Entry(i.toFloat(), (Math.random() * 100).toFloat(), R.drawable.ic_add)
            )
            windEntriesAvgWindSpeed.add(
                Entry(i.toFloat(), (Math.random() * 100).toFloat(), R.drawable.ic_add)
            )
        }

        _windDepthGraphDataSet.value = Pair(
            LineDataSet(windEntriesMaxGust, "Max Gust"),
            LineDataSet(windEntriesAvgWindSpeed, "Average Wind Speed")
        )
    }
}