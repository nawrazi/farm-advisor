package com.enterprise.agino.data.local

import android.provider.ContactsContract
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

    private val farmGsonType: Type = object : TypeToken<ContactsContract.Profile>() {}.type

    fun getFarm(): Flow<Farm?> = prefsDataStore.data.map<Preferences, Farm?> {
        it[Constants.FARM_PREF_KEY]?.let { json ->
            return@map Gson().fromJson(json, farmGsonType)
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