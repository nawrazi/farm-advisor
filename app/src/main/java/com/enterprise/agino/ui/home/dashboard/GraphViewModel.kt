package com.enterprise.agino.ui.home.dashboard

import androidx.lifecycle.*
import com.enterprise.agino.data.repository.GraphRepository
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GraphViewModel @Inject constructor(
    private val repository: GraphRepository
) : ViewModel() {

    private var _graphDataSignal = MutableLiveData<Unit>()
    var graphData = Transformations.switchMap(_graphDataSignal) {
        repository.fetchGraphData().asLiveData()
    }

    // temperature graph dataset
    private val temperatureEntries = mutableListOf<Entry>()
    val temperatureDays = mutableListOf<String>()
    private val _temperatureGraphDataSet =
        MutableLiveData(LineDataSet(temperatureEntries, "Measured Temperature"))
    val temperatureGraphDataSet: LiveData<LineDataSet> = _temperatureGraphDataSet


    // precipitation graph dataset
    private val precipitationEntries = mutableListOf<BarEntry>()
    val precipitationDays = mutableListOf<String>()
    private val _precipitationGraphDataSet =
        MutableLiveData(BarDataSet(precipitationEntries, "Precipitation"))
    val precipitationGraphDataSet: LiveData<BarDataSet> = _precipitationGraphDataSet


    // snow depth graph dataset
    private val snowDepthEntriesSnow = mutableListOf<BarEntry>()
    private val snowDepthEntriesMissing = mutableListOf<BarEntry>()
    val snowDepthDays = mutableListOf<String>()
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
    val windDays = mutableListOf<String>()
    private val _windDepthGraphDataSet = MutableLiveData(
        Pair(
            LineDataSet(windEntriesMaxGust, "Max Gust"),
            LineDataSet(windEntriesAvgWindSpeed, "Average Wind Speed")
        )
    )
    val windGraphDataSet: LiveData<Pair<LineDataSet, LineDataSet>> = _windDepthGraphDataSet

    init {
        _graphDataSignal.value = Unit
    }

    private fun setupSnowDepthGraphData() {

        val data = graphData.value?.value?.timeStamps
        data?.let {
            for (i in data.indices) {
                snowDepthDays.add(data[i].time)
                snowDepthEntriesSnow.add(
                    BarEntry(i.toFloat(), data[i].humidity.toFloat())
                )
                snowDepthEntriesMissing.add(
                    BarEntry(i.toFloat(), data[i].dewTemperature.toFloat())
                )
            }
        }

        _snowDepthGraphDataSet.value = Pair(
            BarDataSet(snowDepthEntriesSnow, "Snow Depth"),
            BarDataSet(snowDepthEntriesMissing, "Missing Data")
        )
    }

    private fun setupTemperatureGraphData() {

        val data = graphData.value?.value?.timeStamps
        data?.let {
            for (i in data.indices) {
                temperatureDays.add(data[i].time)
                temperatureEntries.add(
                    Entry(i.toFloat(), data[i].temperature.toFloat())
                )
            }
        }

        _temperatureGraphDataSet.value = LineDataSet(temperatureEntries, "Measured Temperature")
    }

    private fun setupPrecipitationGraphData() {

        val data = graphData.value?.value?.timeStamps
        data?.let {
            for (i in data.indices) {
                precipitationDays.add(data[i].time)
                precipitationEntries.add(
                    BarEntry(i.toFloat(), data[i].pressure.toFloat())
                )
            }
        }

        _precipitationGraphDataSet.value = BarDataSet(precipitationEntries, "Precipitation")
    }

    private fun setupWindGraphData() {

        val data = graphData.value?.value?.timeStamps
        data?.let {
            for (i in data.indices) {
                windDays.add(data[i].time)
                windEntriesMaxGust.add(
                    Entry(i.toFloat(), data[i].wind.toFloat())
                )
                windEntriesAvgWindSpeed.add(
                    Entry(i.toFloat(), data[i].wind.toFloat())
                )
            }
        }

        _windDepthGraphDataSet.value = Pair(
            LineDataSet(windEntriesMaxGust, "Max Gust"),
            LineDataSet(windEntriesAvgWindSpeed, "Average Wind Speed")
        )
    }

    fun populateGraphData() {
        setupTemperatureGraphData()
        setupSnowDepthGraphData()
        setupPrecipitationGraphData()
        setupWindGraphData()
    }


    fun retry() {
        _graphDataSignal.value = Unit
    }
}