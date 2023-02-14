package com.enterprise.agino.data.remote.dto

data class GraphResponse (
    val type: String,
    val geometry: Geometry,
    val properties: Properties
)

data class Geometry (
    val type: String,
    val coordinates: List<Long>
)

data class Properties (
    val meta: Meta,
    val timeseries: List<TimeSeries>
)

data class Meta (
    val updatedAt: String,
    val units: Units
)

data class Units (
    val airPressureAtSeaLevel: String,
    val airTemperature: String,
    val airTemperatureMax: String,
    val airTemperatureMin: String,
    val cloudAreaFraction: String,
    val cloudAreaFractionHigh: String,
    val cloudAreaFractionLow: String,
    val cloudAreaFractionMedium: String,
    val dewPointTemperature: String,
    val fogAreaFraction: String,
    val precipitationAmount: String,
    val relativeHumidity: String,
    val ultravioletIndexClearSky: String,
    val windFromDirection: String,
    val windSpeed: String
)

data class TimeSeries (
    val time: String,
    val data: Data
)

data class Data (
    val instant: Instant,
    val next12_Hours: Next12Hours? = null,
    val next1_Hours: Next1Hours? = null,
    val next6_Hours: Next6Hours? = null
)

data class Instant (
    val details: Map<String, Double>
)

data class Next12Hours (
    val summary: Summary
)

data class Summary (
    val symbolCode: SymbolCode
)

enum class SymbolCode {
    ClearskyDay,
    ClearskyNight,
    Cloudy,
    FairDay,
    FairNight,
    Fog,
    Lightrain,
    LightrainshowersDay,
    PartlycloudyDay,
    PartlycloudyNight
}

data class Next1Hours (
    val summary: Summary,
    val details: Next1HoursDetails
)

data class Next1HoursDetails (
    val precipitationAmount: Long
)

data class Next6Hours (
    val summary: Summary,
    val details: Next6HoursDetails
)

data class Next6HoursDetails (
    val airTemperatureMax: Double,
    val airTemperatureMin: Double,
    val precipitationAmount: Double
)
