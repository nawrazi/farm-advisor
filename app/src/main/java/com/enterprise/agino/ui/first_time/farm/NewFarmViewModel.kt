package com.enterprise.agino.ui.first_time.farm

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enterprise.agino.common.Resource
import com.enterprise.agino.data.repository.FarmRepository
import com.enterprise.agino.domain.model.form.AddFarmForm
import com.enterprise.agino.utils.reEmit
import com.tomtom.sdk.location.GeoPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewFarmViewModel @Inject constructor(private val farmRepository: FarmRepository) :
    ViewModel() {
    val searchResults = MutableLiveData<List<Pair<String, GeoPoint>>>()
    val farmName = MutableLiveData<String>()
    val farmAddress = MutableLiveData<GeoPoint?>(null)

    val isCreateFarmButtonEnabled = MediatorLiveData<Boolean>()

    private val submitFormSignal = MutableSharedFlow<Unit>()
    val formSubmissionResult = MutableSharedFlow<Resource<Unit>>()
    lateinit var isLoading: StateFlow<Boolean>

    init {
        isCreateFarmButtonEnabled.addSource(farmName) {
            isCreateFarmButtonEnabled.value =
                (!farmName.value.isNullOrBlank()) and (farmAddress.value != null)
        }
        isCreateFarmButtonEnabled.addSource(farmAddress) {
            isCreateFarmButtonEnabled.value =
                (!farmName.value.isNullOrBlank()) and (farmAddress.value != null)
        }
        viewModelScope.launch {
            launch {
                submitFormSignal.collect {
                    formSubmissionResult.emitAll(submit())
                }
            }
            launch {
                isLoading = formSubmissionResult.map { it is Resource.Loading }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(),
                    initialValue = false
                )
            }
        }
    }

    private fun submit(): Flow<Resource<Unit>> = flow {
        farmRepository.createFarm(AddFarmForm(farmName.value!!, farmAddress.value!!))
            .reEmit(this).collect()
    }

    fun submitForm() {
        viewModelScope.launch {
            submitFormSignal.emit(Unit)
        }
    }

}