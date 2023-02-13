package com.enterprise.agino.ui.home

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.enterprise.agino.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class HomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun testNavigateToNewFieldFragment() {
        launchFragmentInContainer<HomeFragment>()

        // onView(withId(R.id.addFieldButton)).perform(click())

        // Verify that navigating to the NewFieldFragment is successful
        // You can add further assertions to check the navigation and view components of the NewFieldFragment
    }

    @Test
    fun testNavigateToNewFarmFragment() {
        launchFragmentInContainer<HomeFragment>()

        // onView(withId(R.id.createFarmButton)).perform(click())

        // TODO: Verify that navigating to the NewFarmFragment is successful
        // TODO: You can add further assertions to check the navigation and view components of the NewFarmFragment
    }
}
