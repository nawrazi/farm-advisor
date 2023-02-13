package com.enterprise.agino.ui.fielddetails

import com.enterprise.agino.di.AppModule
import com.enterprise.agino.launchFragmentInHiltContainer
import com.enterprise.agino.ui.addnewsensor.AddNewSensorFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@UninstallModules(AppModule::class)
@ExperimentalCoroutinesApi
class FieldDetailsScreenTest {
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