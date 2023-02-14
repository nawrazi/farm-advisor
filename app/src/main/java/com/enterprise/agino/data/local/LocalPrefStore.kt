package com.enterprise.agino.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.enterprise.agino.common.Constants
import com.enterprise.agino.domain.model.Farm
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.lang.reflect.Type

class LocalPrefStore(private val prefsDataStore: DataStore<Preferences>) {

    private val farmGsonType: Type = object : TypeToken<Farm>() {}.type

    fun getFarm(): Flow<Farm?> = prefsDataStore.data.map {
        it[Constants.FARM_PREF_KEY]?.let { json ->
            val res = Gson().fromJson<Farm>(json, farmGsonType)
            return@map res
        }

        return@map null
    }.flowOn(Dispatchers.IO)

    suspend fun setFarm(farm: Farm?) {
        prefsDataStore.edit { mutablePreferences ->
            if (farm == null) {
                mutablePreferences.remove(Constants.FARM_PREF_KEY)
                return@edit
            }

            mutablePreferences[Constants.FARM_PREF_KEY] = Gson().toJson(farm)
        }
    }
}