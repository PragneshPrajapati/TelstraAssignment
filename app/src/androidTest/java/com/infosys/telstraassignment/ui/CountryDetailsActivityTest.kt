package com.infosys.telstraassignment.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.infosys.telstraassignment.R
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class CountryDetailsActivityTest : TestCase() {

    @get:Rule
    var activityRule: ActivityTestRule<CountryDetailsActivity> =
        ActivityTestRule(CountryDetailsActivity::class.java)

    @get:Rule
    val mainActivityRule = IntentsTestRule(CountryDetailsActivity::class.java)

    @Test
    fun launchActivityTest() {
        onView(withId(R.id.vParent))
            .perform(click())

        intended(hasComponent(CountryDetailsActivity::class.java.name))
    }
}
