package com.enterprise.agino.ui.addnewsensor

import com.enterprise.agino.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddNewSensorFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun sampletest() {
        launchFragmentInHiltContainer<AddNewSensorFragment> {

        }
    }
}