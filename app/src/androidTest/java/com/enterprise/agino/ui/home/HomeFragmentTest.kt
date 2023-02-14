package com.enterprise.agino.ui.home

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.enterprise.agino.R
import com.enterprise.agino.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class HomeFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule { this }

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickAddFieldButton_navigateToNewFieldFragment2() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<HomeFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.addFieldButton)).perform(click())

        verify(navController).navigate(
            HomeFragmentDirections.actionHomeFragmentToNewFieldFragment2("")
        )
    }
}