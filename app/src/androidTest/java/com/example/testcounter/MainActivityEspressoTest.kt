package com.example.testcounter

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.geekbrains.tests.BuildConfig
import com.geekbrains.tests.R
import com.geekbrains.tests.view.search.FAKE
import com.geekbrains.tests.view.search.MainActivity
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityEspressoTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup(){
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun searchEditText_IsVisible(){
        onView(withId(R.id.searchEditText)).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(replaceText("algol"), closeSoftKeyboard())
        onView(withId(R.id.searchEditText)).check(matches(withText("algol")))
    }

    @Test
    fun toDetailsActivityButton_Visibility(){
        onView(withId(R.id.toDetailsActivityButton)).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.toDetailsActivityButton)).check(matches(withText("to details")))
    }

    @Test
    fun progressBar_Visible(){
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(replaceText("algol"), closeSoftKeyboard())
        onView(withId(R.id.searchEditText)).perform(pressImeActionButton())
        onView(isRoot()).perform(delay(500))
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
    }

    @Test
    fun activitySearch_IsWorking() {
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(replaceText("algol"), closeSoftKeyboard())
        onView(withId(R.id.searchEditText)).perform(pressImeActionButton())
        if(BuildConfig.TYPE == FAKE){
        onView(withId(R.id.totalCountTextView)).check(matches(withText("Number of results: 42")))
        } else{
            onView(isRoot()).perform(delay(4000))
            onView(withId(R.id.totalCountTextView)).check(matches(withText("Number of results: 2977")))
        }
   }

    private fun delay(millis: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for ${millis/1000} seconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(millis)
            }
        }
    }

    @After
    fun close(){
        scenario.close()
    }
}