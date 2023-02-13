package com.enterprise.agino.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enterprise.agino.domain.model.Farm
import com.enterprise.agino.domain.repository.IFarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val farmRepository: IFarmRepository) : ViewModel() {
    val farm: StateFlow<Farm?> =
        farmRepository.getFarm().stateIn(viewModelScope, SharingStarted.Lazily, null)
}