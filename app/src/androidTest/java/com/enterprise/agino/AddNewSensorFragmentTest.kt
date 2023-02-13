import androidx.fragment.app.testing.FragmentScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.enterprise.agino.R
import com.enterprise.agino.ui.addnewsensor.AddNewSensorFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@HiltAndroidTest
class AddNewSensorFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testAddNewSensorFragment() {
        // Launch the fragment
        val scenario = FragmentScenario.launchInContainer(AddNewSensorFragment::class.java)


        // TODO: tailor it to the fragement in question
        
        // Check if the name EditText is displayed
        onView(withId(R.id.edit_text_name)).check(matches(isDisplayed()))

        // Enter a name in the name EditText
        onView(withId(R.id.edit_text_name)).perform(typeText("Sensor 1"), closeSoftKeyboard())

        // Check if the type EditText is displayed
        onView(withId(R.id.edit_text_type)).check(matches(isDisplayed()))

        // Enter a type in the type EditText
        onView(withId(R.id.edit_text_type)).perform(typeText("Temperature"), closeSoftKeyboard())

        // Check if the description EditText is displayed
        onView(withId(R.id.edit_text_description)).check(matches(isDisplayed()))

        // Enter a description in the description EditText
        onView(withId(R.id.edit_text_description)).perform(typeText("This is a temperature sensor."), closeSoftKeyboard())

        // Check if the add button is displayed
        onView(withId(R.id.button_add)).check(matches(isDisplayed()))

        // Click the add button
        onView(withId(R.id.button_add)).perform(click())
    }
}
