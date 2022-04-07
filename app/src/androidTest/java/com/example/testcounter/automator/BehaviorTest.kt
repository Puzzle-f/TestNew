package com.example.testcounter.automator

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.geekbrains.tests.R
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class BehaviorTest {

    @Before
    fun setup() {
//Для начала сворачиваем все приложения, если у нас что-то запущено
        uiDevice.pressHome()
//Запускаем наше приложение
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)

        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)//Чистим бэкстек от запущенных ранее Активити
                context.startActivity(intent)
//Ждем, когда приложение откроется на смартфоне чтобы начать тестировать его элементы
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
        Log.d("","packageName = ${context.packageName}")
    }

    @Test
    fun test_SearchIsPositive() {
//Через uiDevice находим editText
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
//Устанавливаем значение
        editText.text = "UiAutomator"
//Отправляем запрос через Espresso
        Espresso.onView(ViewMatchers.withId(R.id.searchEditText))
            .perform(ViewActions.pressImeActionButton())
//Ожидаем конкретного события: появления текстового поля totalCountTextView.
//Это будет означать, что сервер вернул ответ с какими-то данными, то есть запрос отработал.
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
        TIMEOUT
        )
//Убеждаемся, что сервер вернул корректный результат. Обратите внимание, что количество
//результатов может варьироваться во времени, потому что количество репозиториев постоянно меняется.
        Assert.assertEquals(changedText.text.toString(), "Number of results: 668")
    }

    companion object {
        private const val TIMEOUT = 5000L
    }

    private val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName

}
