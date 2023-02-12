package com.enterprise.agino.ui.first_time.farm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomtom.sdk.location.GeoPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewFarmViewModel @Inject constructor() : ViewModel() {
    val searchResults = MutableLiveData<List<Pair<String, GeoPoint>>>()
}