package com.enterprise.agino.ui.onboarding.verificationcode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VerificationCodeViewModel @Inject constructor() : ViewModel() {
    val verificationCode = MutableLiveData<String>()

    val isContinueButtonEnabled = Transformations.map(verificationCode) {
        it.length == 6
    }
}