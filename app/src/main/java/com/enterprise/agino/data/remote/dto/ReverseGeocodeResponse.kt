package com.enterprise.agino.data.remote.dto

data class ReverseGeocodeResponse(
    val summary: Summary,
    val addresses: List<AddressElement>
)

data class AddressElement(
    val address: AddressAddress,
    val position: String,
    val dataSources: DataSources,
    val entityType: String
)

data class AddressAddress (
    val routeNumbers: List<Any?>,
    val countryCode: String,
    val countrySubdivision: String,
    val countrySecondarySubdivision: String,
    val municipality: String,
    val country: String,
    val countryCodeISO3: String,
    val freeformAddress: String,
    val boundingBox: BoundingBox
)

data class BoundingBox (
    val northEast: String,
    val southWest: String,
    val entity: String
)

data class DataSources (
    val geometry: Geometry
)

data class Geometry (
    val id: String
)

data class Summary (
    val queryTime: Long,
    val numResults: Long
)
