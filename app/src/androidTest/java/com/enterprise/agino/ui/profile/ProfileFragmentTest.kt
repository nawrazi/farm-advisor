package com.enterprise.agino.ui.profile

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.enterprise.agino.R
import com.enterprise.agino.launchFragmentInHiltContainer
import com.enterprise.agino.ui.home.HomeFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ProfileFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule { this }

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickDeleteAccountButton_navigateOnBoarding1Fragment() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<HomeFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.delete_account_button))
            .perform(ViewActions.click())

        Mockito.verify(navController).navigate(
            ProfileFragmentDirections.actionProfileFragmentToOnBoarding1Fragment()
        )
    }

    @Test
    fun clickTermsAndConditions_navigateOnBoarding2Fragment() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<HomeFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.terms_and_condtions)).perform(ViewActions.click())

        Mockito.verify(navController).navigate(
            ProfileFragmentDirections.actionProfileFragmentToOnBoarding2Fragment("", false)
        )
    }
}