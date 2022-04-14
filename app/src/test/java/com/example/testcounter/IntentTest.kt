package com.example.testcounter

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.geekbrains.tests.TEST_NUMBER
import com.geekbrains.tests.view.details.DetailsActivity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Test

class IntentTest {

    @Test
    fun activityCreateIntent_NotNull() {
        val context: Context = ApplicationProvider.getApplicationContext()
        val intent = DetailsActivity.getIntent(context, 0)
        Log.e("","intent = $intent" )
        assertNotNull(intent)
    }

    @Test
    fun activityCreateIntent_HasExtras() {
        val context: Context = ApplicationProvider.getApplicationContext()
        val intent = DetailsActivity.getIntent(context, 0)
        val bundle = intent.extras
        assertNotNull(bundle)
    }

//    @Test
//    fun activityCreateIntent_HasCount() {
//        val context: Context = ApplicationProvider.getApplicationContext()
//        val intent = DetailsActivity.getIntent(context, TEST_NUMBER)
//        val bundle = intent.extras
//        assertEquals(TEST_NUMBER, bundle?.getInt(DetailsActivity.TOTAL_COUNT_EXTRA, 0))
//    }
}