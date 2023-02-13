package com.enterprise.agino.ui.first_time.field

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enterprise.agino.common.Resource
import com.enterprise.agino.data.repository.FarmRepository
import com.enterprise.agino.data.repository.FieldRepository
import com.enterprise.agino.domain.model.form.AddFarmForm
import com.enterprise.agino.domain.model.form.AddFieldForm
import com.tomtom.sdk.search.reversegeocoder.ReverseGeocoderResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewFieldViewModel @Inject constructor(
    private val fieldRepository: FieldRepository,
    private val farmRepository: FarmRepository,
) : ViewModel() {
    val fieldName = MutableLiveData<String>()
    val altitude = MutableLiveData<String>()
    val isAddFieldButtonEnabled = MediatorLiveData<Boolean>()

    private val submitFormSignal = MutableSharedFlow<Unit>()
    val formSubmissionResult = MutableSharedFlow<Resource<Unit>>()
    lateinit var isLoading: StateFlow<Boolean>

    init {
        isAddFieldButtonEnabled.addSource(fieldName) {
            isAddFieldButtonEnabled.value =
                (!fieldName.value.isNullOrBlank()) and (altitude.value != null)
        }

        isAddFieldButtonEnabled.addSource(altitude) {
            isAddFieldButtonEnabled.value =
                (!fieldName.value.isNullOrBlank()) and (altitude.value != null)
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

        var farmId = farmRepository.getFarm().first()?.farmID ?: ""

        fieldRepository.addField(AddFieldForm(fieldName.value!!, altitude.value!!.toInt(), farmId))
            .transform<Resource<Unit>, Resource<Unit>> { resource ->
                when (resource) {
                    is Resource.Loading -> this@flow.emit(Resource.Loading())
                    is Resource.Success -> this@flow.emit(Resource.Success(data = Unit))
                    is Resource.Error ->
                        this@flow.emit(Resource.Error(resource.message ?: "Unknown error"))
                }
            }.collect()
    }

    fun submitForm() {
        viewModelScope.launch {
            submitFormSignal.emit(Unit)
        }
    }
}