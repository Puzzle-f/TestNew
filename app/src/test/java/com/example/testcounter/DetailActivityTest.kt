package com.example.testcounter

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.R
import com.geekbrains.tests.TEST_NUMBER_OF_RESULTS_MINUS_1
import com.geekbrains.tests.TEST_NUMBER_OF_RESULTS_PLUS_1
import com.geekbrains.tests.TEST_NUMBER_OF_RESULTS_ZERO
import com.geekbrains.tests.view.details.DetailsActivity
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class DetailActivityTest {

    private lateinit var scenario: ActivityScenario<DetailsActivity>
    private lateinit var context: Context

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(DetailsActivity::class.java)
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity {
            assertNotNull(it)
        }
    }

    @Test
    fun activity_IsResumed() {
        assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun activityTextView_NotNull() {
        scenario.onActivity {
            val totalCountTextView =
                it.findViewById<TextView>(R.id.totalCountTextView)
            assertNotNull(totalCountTextView)
        }
    }

    @Test
    fun activityTextView_HasText() {
        val assertion: ViewAssertion = matches(withText(TEST_NUMBER_OF_RESULTS_ZERO))
        onView(withId(R.id.totalCountTextView)).check(assertion)
    }

    @Test
    fun activityTextView_IsVisible() {
        scenario.onActivity {
            val totalCountTextView =
                it.findViewById<TextView>(R.id.totalCountTextView)
            assertEquals(View.VISIBLE, totalCountTextView.visibility)
        }
    }

    @Test
    fun activityButtons_AreVisible() {
        scenario.onActivity {
            val decrementButton =
                it.findViewById<Button>(R.id.decrementButton)
            assertEquals(View.VISIBLE, decrementButton.visibility)
            val incrementButton =
                it.findViewById<Button>(R.id.incrementButton)
            assertEquals(View.VISIBLE, incrementButton.visibility)
        }
    }

    @Test
    fun activityButtonIncrement_IsWorking() {
        scenario.onActivity {
            val incrementButton =
                it.findViewById<Button>(R.id.incrementButton)
            val totalCountTextView =
                it.findViewById<TextView>(R.id.totalCountTextView)
            incrementButton.performClick()
            assertEquals(TEST_NUMBER_OF_RESULTS_PLUS_1, totalCountTextView.text)
        }
    }

    @Test
    fun activityButtonDecrement_IsWorking() {
        scenario.onActivity {
            val decrementButton =
                it.findViewById<Button>(R.id.decrementButton)
            val totalCountTextView =
                it.findViewById<TextView>(R.id.totalCountTextView)
            decrementButton.performClick()
            assertEquals(TEST_NUMBER_OF_RESULTS_MINUS_1, totalCountTextView.text)
        }
    }

    @Test
    fun activityCreateIntent_HasExtras() {
        val intent = DetailsActivity.getIntent(context, 0)
        val bundle = intent.extras
        assertNotNull(bundle)
    }

//    @Test
//    fun activityCreateIntent_HasCount() {
//
//        val intent = DetailsActivity.getIntent(context, TEST_NUMBER)
//        val bundle = intent.extras
//        assertEquals(
//            TEST_NUMBER, bundle?.getInt(
//                DetailsActivity.TOTAL_COUNT_EXTRA,
//                0
//            )
//        )
//    }

    @After
    fun close() {
        scenario.close()
    }


}