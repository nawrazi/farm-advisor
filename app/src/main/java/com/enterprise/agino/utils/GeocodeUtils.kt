package com.enterprise.agino.utils

import com.enterprise.agino.common.Constants
import com.tomtom.sdk.location.GeoPoint

fun toGeocodeUrl(geoPoint: GeoPoint): String =
    "https://api.tomtom.com/search/2/reverseGeocode/${geoPoint.latitude},${geoPoint.longitude}.json?key=${Constants.MAP_KEY}&radius=100"
