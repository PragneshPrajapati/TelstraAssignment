package com.infosys.telstraassignment.ui

import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.infosys.telstraassignment.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4ClassRunner::class)
class CountryDetailsFragmentTest {

    @get:Rule
    var activityRule: ActivityTestRule<CountryDetailsActivity> =
        ActivityTestRule(CountryDetailsActivity::class.java)

    @Before
    fun setUp() {
        ActivityScenario.launch(CountryDetailsActivity::class.java)

        launchFragmentInContainer<CountryDetailsFragment>(
            themeResId = R.style.Theme_TelstraAssignment
        )
    }

    @Test
    @Throws(InterruptedException::class)
    fun testVisibilityProgressBar() {
        Espresso.onView(ViewMatchers.withId(R.id.loading))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(
                        activityRule.activity.window.decorView
                    )
                )
            )
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    @Throws(InterruptedException::class)
    fun testVisibilityRecyclerView() {
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.rvCountryDetails))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(
                        activityRule.activity.window.decorView
                    )
                )
            )
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    @Throws(InterruptedException::class)
    fun testCaseForRecyclerScroll() {
        Thread.sleep(1000)
        val recyclerView =
            activityRule.activity.findViewById<RecyclerView>(R.id.rvCountryDetails)
        val itemCount =
            Objects.requireNonNull(recyclerView.adapter!!).itemCount
        Espresso.onView(ViewMatchers.withId(R.id.rvCountryDetails))
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
        Espresso.onView(ViewMatchers.withId(R.id.rvCountryDetails))
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
                    ViewActions.click()
                )
            )
    }

    @Test
    @Throws(InterruptedException::class)
    fun testCaseForRecyclerItemView() {
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.rvCountryDetails))
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
                            ViewMatchers.withId(R.id.layoutCountryDetails),
                            ViewMatchers.isDisplayed()
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
