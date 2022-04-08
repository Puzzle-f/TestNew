package com.example.testcounter.automator

import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class OpenOtherAppsTest {
    private lateinit var appViews:UiScrollable
    private val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())
//    Открываем настройки
   @Before
   fun setup(){
//    uiDevice.pressHome()
    appViews = UiScrollable(UiSelector().scrollable(true))
}

//    @Test
//    fun test_OpenSettings(){
//        uiDevice.swipe(uiDevice.displayWidth/2, 300, uiDevice.displayWidth/2, 100, 5)
//        val settingsApp = appViews
//            .getChildByText(
//                UiSelector()
//                    .className(TextView::class.java.name),
//                "Settings"
//            )
//        settingsApp.clickAndWaitForNewWindow()
//        val settingsValidation =
//            uiDevice.findObject(UiSelector().packageName("com.android.settings"))
//        Assert.assertTrue(settingsValidation.exists())
//    }

//    смотрим котика
    @Test
    fun open_Kat(){
        val cat = appViews.getChildByText(
            UiSelector()
                .className(TextView::class.java.name),
            "Kitties and Puppies"
        )
    cat.clickAndWaitForNewWindow()
    }
}