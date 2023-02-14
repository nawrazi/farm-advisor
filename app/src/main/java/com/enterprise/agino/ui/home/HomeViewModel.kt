package com.enterprise.agino.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enterprise.agino.common.Resource
import com.enterprise.agino.domain.model.Farm
import com.enterprise.agino.domain.repository.IFarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val farmRepository: IFarmRepository) : ViewModel() {
    val farm: StateFlow<Farm?> =
        farmRepository.getFarm().stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _errorMessage = Channel<String>(1, BufferOverflow.DROP_LATEST)
    val errorMessage: Flow<String> =
        _errorMessage.receiveAsFlow().shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000))

    private val refreshSignal = MutableSharedFlow<Unit>()

    val loadDataSignal: Flow<Unit> = flow {
        emit(Unit)
        emitAll(refreshSignal)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val fetchResult: StateFlow<Resource<Unit>> = loadDataSignal.flatMapLatest {
        farmRepository.fetchFarm()
    }.onEach {
        if (it is Resource.Error)
            _errorMessage.trySend(it.errMsg)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        Resource.Loading()
    )

    val isLoading: StateFlow<Boolean> =
        fetchResult.transform<Resource<Unit>, Boolean> { it is Resource.Loading }
            .stateIn(viewModelScope, SharingStarted.Eagerly, true)
}