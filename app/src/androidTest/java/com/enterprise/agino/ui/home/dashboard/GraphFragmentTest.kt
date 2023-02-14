package com.enterprise.agino.ui.home.dashboard

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.enterprise.agino.R
import com.enterprise.agino.launchFragmentInHiltContainer
import com.enterprise.agino.ui.home.HomeFragment
import com.enterprise.agino.ui.home.HomeFragmentDirections
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class GraphFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule { this }

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickAddSensorButton_navigateToAddSensorFragment() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<GraphFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.add_sensor_button)).perform(ViewActions.click())

        Mockito.verify(navController).navigate(
            GraphFragmentDirections.actionGraphFragmentToAddNewSensorFragment("")
        )
    }

    @Test
    fun clickBackButton_navigateToHomeFragment() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<GraphFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.backButtonField)).perform(ViewActions.click())

        Mockito.verify(navController).navigateUp()
    }
}