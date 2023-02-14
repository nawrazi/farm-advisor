package com.enterprise.agino.ui.addnewsensor

import androidx.lifecycle.*
import com.enterprise.agino.common.Resource
import com.enterprise.agino.domain.model.AddSensorForm
import com.enterprise.agino.domain.repository.ISensorRepository
import com.enterprise.agino.utils.reEmit
import com.tomtom.sdk.location.GeoPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddNewSensorViewModel @Inject constructor(
    private val sensorRepository: ISensorRepository, state: SavedStateHandle
) : ViewModel() {
    val searchResults = MutableLiveData<List<Pair<String, GeoPoint>>>()
    val serialNumber = MutableLiveData<String>()
    val location = MutableLiveData<GeoPoint>()
    val fieldId: String = state.get<String>("fieldId")!!
    val defaultGDD = MutableLiveData<String>()
    val sensorInstallationDate = MutableLiveData<Date>()
    val lastFieldCuttingDate = MutableLiveData<Date>()

    val isCreateSensorButtonEnabled = MediatorLiveData<Boolean>()

    private val submitFormSignal = MutableSharedFlow<Unit>()
    val formSubmissionResult = MutableSharedFlow<Resource<Unit>>()
    lateinit var isLoading: StateFlow<Boolean>

    init {
        val formValidationObserver = {
            isCreateSensorButtonEnabled.value =
                (!serialNumber.value.isNullOrBlank()) and (location.value != null) and
                        (!defaultGDD.value.isNullOrBlank()) and
                        (sensorInstallationDate.value != null) and
                        (lastFieldCuttingDate.value != null)
        }

        for (field in listOf(
            serialNumber,
            location,
            defaultGDD,
            sensorInstallationDate,
            lastFieldCuttingDate
        )) {
            isCreateSensorButtonEnabled.addSource(field) { formValidationObserver() }
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
        sensorRepository.addSensor(
            AddSensorForm(
                serialNumber = serialNumber.value!!,
                location = location.value!!,
                fieldId = fieldId,
                defaultGDD = defaultGDD.value!!.toInt(),
                sensorInstallationDate = sensorInstallationDate.value!!,
                lastFieldCuttingDate = lastFieldCuttingDate.value!!,
            )
        ).reEmit(this@flow).collect()
    }

    fun submitForm() {
        viewModelScope.launch {
            submitFormSignal.emit(Unit)
        }
    }

}