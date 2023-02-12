package com.enterprise.agino.ui.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {
    var countryCode: String = "251"
    val phoneNumber = MutableLiveData<String>()

    val isContinueBtnEnabled = Transformations.map(phoneNumber) {
        it.length == 10
    }
}