package com.enterprise.agino.ui.first_time.field

import androidx.lifecycle.*
import com.enterprise.agino.common.Resource
import com.enterprise.agino.data.repository.FarmRepository
import com.enterprise.agino.data.repository.FieldRepository
import com.enterprise.agino.domain.model.form.AddFieldForm
import com.enterprise.agino.utils.reEmit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewFieldViewModel @Inject constructor(
    private val fieldRepository: FieldRepository,
    private val savedStateHandle: SavedStateHandle
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
        val farmId = savedStateHandle.get<String>("farmId")!!

        fieldRepository.addField(AddFieldForm(fieldName.value!!, altitude.value!!.toInt(), farmId))
            .reEmit(this).collect()
    }

    fun submitForm() {
        viewModelScope.launch {
            submitFormSignal.emit(Unit)
        }
    }
}