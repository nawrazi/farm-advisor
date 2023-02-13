package com.enterprise.agino.common

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    const val BASE_URL = "http://192.168.43.252:7071/"
    const val MAP_KEY = "QKG5sZdSQCFSitlPKDsIHZ9FV7ZGZg4g"

    val FARM_PREF_KEY by lazy { stringPreferencesKey("farm") }
    const val USER_PREFERENCES_NAME = "user_prefs"
}