package com.example.testcounter.automator

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.geekbrains.tests.view.details.DetailsActivity
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class BehaviorTest {

    private val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName
    private lateinit var scenarioDetails: ActivityScenario<DetailsActivity>


    @Before
    fun setup() {
        scenarioDetails = ActivityScenario.launch(DetailsActivity::class.java)
        uiDevice.pressHome()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)//Чистим бэкстек от запущенных ранее Активити
        context.startActivity(intent)
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
    }

    @Test
    fun test_SearchIsPositive() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = "mojombo"
        val searchButton = uiDevice.findObject(By.res(packageName, "search_button"))
        searchButton.click()
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "countProjects")),
                TIMEOUT
            )
        Assert.assertEquals(changedText.text.toString(), "25")
    }

    @Test
    fun test_OpenDetailsScreen() {
        val toDetails = uiDevice.findObject(
            By.res(
                packageName,
                "toDetailsActivityButton"
            )
        )
        toDetails.click()
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        Assert.assertEquals(changedText.text, "Number of results: 0")
    }

    @Test
    fun test_Correct_Display() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.click()
        editText.text = "mojombo"
        val searchButton = uiDevice.findObject(By.res(packageName, "search_button"))
        searchButton.click()

        uiDevice.wait(
            Until.findObject(By.text("25")),
            TIMEOUT
        )
        val toDetails = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))
        toDetails.click()
        uiDevice.wait(
            Until.findObject(By.res("totalCountTextView")), TIMEOUT
        )
        val changedText = uiDevice.findObject(By.res(packageName, "totalCountTextView"))
        Assert.assertEquals(changedText.text, "Number of results: 25")
    }

    @Test
    fun details_Increment(){
        val toDetails = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))
        toDetails.click()
        uiDevice.wait(
            Until.findObject(By.res("totalCountTextView")), TIMEOUT
        )
        val changedText = uiDevice.findObject(By.res(packageName, "totalCountTextView"))
        Assert.assertEquals(changedText.text, "Number of results: 0")
        val increment = uiDevice.findObject(By.res(packageName, "incrementButton"))
        increment.click()
        increment.click()
        increment.click()
        Assert.assertEquals(changedText.text, "Number of results: 3")
    }

    @Test
    fun details_Decrement(){
        val toDetails = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))
        toDetails.click()
        uiDevice.wait(
            Until.findObject(By.res("totalCountTextView")), TIMEOUT
        )
        val changedText = uiDevice.findObject(By.res(packageName, "totalCountTextView"))
        Assert.assertEquals(changedText.text, "Number of results: 0")
        val decrement = uiDevice.findObject(By.res(packageName, "decrementButton"))
        decrement.click()
        decrement.click()
        decrement.click()
        Assert.assertEquals(changedText.text, "Number of results: -3")
    }


    @After
    fun close() {
        scenarioDetails.close()
    }

    companion object {
        private const val TIMEOUT = 5000L
    }

}
