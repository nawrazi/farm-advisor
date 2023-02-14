package com.enterprise.agino.common

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    const val BASE_URL = "http://192.167.4:7071/"
    const val MAP_KEY = "QKG5sZdSQCFSitlPKDsIHZ9FV7ZGZg4g"
    const val GRAPH_API = "https://api.met.no/weatherapi/locationforecast/2.0/complete?lat=05&lon=10&altitude=100"

    val FARM_PREF_KEY by lazy { stringPreferencesKey("farm") }
    const val USER_PREFERENCES_NAME = "user_prefs"
}