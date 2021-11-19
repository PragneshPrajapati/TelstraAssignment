package com.infosys.telstraassignment.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.infosys.telstraassignment.R
import junit.framework.TestCase
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
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

    @Test
    @Throws(InterruptedException::class)
    fun testVisibilityProgressBar() {
        onView(withId(R.id.loading))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(
                        activityRule.activity.window.decorView
                    )
                )
            )
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    @Throws(InterruptedException::class)
    fun testVisibilityRecyclerView() {
        Thread.sleep(2000)
        onView(withId(R.id.rvCountryDetails))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(
                        activityRule.activity.window.decorView
                    )
                )
            )
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    @Throws(InterruptedException::class)
    fun testCaseForRecyclerScroll() {
        Thread.sleep(1000)
        val recyclerView =
            activityRule.activity.findViewById<RecyclerView>(R.id.rvCountryDetails)
        val itemCount =
            Objects.requireNonNull(recyclerView.adapter!!).itemCount
        onView(withId(R.id.rvCountryDetails))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(
                        activityRule.activity.window.decorView
                    )
                )
            )
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(itemCount - 1))
    }

    @Test
    @Throws(InterruptedException::class)
    fun testCaseForRecyclerClick() {
        Thread.sleep(1000)
        onView(withId(R.id.rvCountryDetails))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(
                        activityRule.activity.window.decorView
                    )
                )
            )
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
    }

    @Test
    @Throws(InterruptedException::class)
    fun testCaseForRecyclerItemView() {
        Thread.sleep(1000)
        onView(withId(R.id.rvCountryDetails))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(
                        activityRule.activity.window.decorView
                    )
                )
            )
            .check(
                ViewAssertions.matches(
                    1.withViewAtPosition(
                        Matchers.allOf(
                            withId(R.id.layoutCountryDetails), isDisplayed()
                        )
                    )
                )
            )
    }

    private fun Int.withViewAtPosition(
        itemMatcher: Matcher<View?>
    ): Matcher<View?> {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val viewHolder =
                    recyclerView.findViewHolderForAdapterPosition(this@withViewAtPosition)
                return viewHolder != null && itemMatcher.matches(viewHolder.itemView)
            }
        }
    }
}
