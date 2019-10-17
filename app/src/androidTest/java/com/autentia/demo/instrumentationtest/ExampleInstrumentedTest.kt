package com.autentia.demo.instrumentationtest

import android.app.Application
import android.view.View
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()
    private val appContext = ApplicationProvider.getApplicationContext<Application>()

    @Test
    fun useAppContext() {
        val appContext = ApplicationProvider.getApplicationContext<Application>()
        assertEquals("com.autentia.demo.instrumentationtest", appContext.packageName)
    }

    @Test
    fun usernameAndPasswordFieldShowErrorMessageIfAreEmptyWhenPressedLoginButton() {
        // When
        onView(withId(R.id.button_login)).perform(click())

        // Then
        onView(withId(R.id.field_username)).check(matches(checkErrorText<TextInputLayout> {
            hasErrorText(it)
        }))
        onView(withId(R.id.field_password)).check(matches(checkErrorText<TextInputLayout> {
            hasErrorText(it)
        }))
    }

    @Test
    fun usernameFieldShowsErrorMessageIfEmptyValueWhenPressedLoginButton() {
        // When
        onView(withId(R.id.input_field_password))
            .perform(typeText("password"))
            .perform(closeSoftKeyboard())

        onView(withId(R.id.button_login)).perform(click())

        // Then
        onView(withId(R.id.field_username)).check(matches(checkErrorText<TextInputLayout> {
            hasErrorText(it)
        }))
        onView(withId(R.id.field_password)).check(matches(checkErrorText<TextInputLayout> {
            hasNotErrorText(it)
        }))
    }

    @Test
    fun usernameAndPasswordFieldsDoNotShowError() {
        // When
        onView(withId(R.id.input_field_username))
            .perform(typeText("username"))
            .perform(closeSoftKeyboard())

        onView(withId(R.id.input_field_password))
            .perform(typeText("password"))
            .perform(closeSoftKeyboard())

        onView(withId(R.id.button_login)).perform(click())

        // Then
        onView(withId(R.id.field_username)).check(matches(checkErrorText<TextInputLayout> {
            hasNotErrorText(it)
        }))
        onView(withId(R.id.field_password)).check(matches(checkErrorText<TextInputLayout> {
            hasNotErrorText(it)
        }))
    }

    private inline fun <reified T : View> checkErrorText(
        crossinline condition: (view: T) -> Boolean
    ): BaseMatcher<View> {
        return object : BaseMatcher<View>() {

            override fun describeTo(description: Description) {}

            override fun matches(item: Any): Boolean {
                val textInputLayout = item as T
                return condition(textInputLayout)
            }
        }
    }

    private fun hasNotErrorText(it: TextInputLayout) = it.error.isNullOrEmpty()

    private fun hasErrorText(it: TextInputLayout) =
        it.error?.toString()?.equals(appContext.getString(R.string.empty_field_error)) ?: false

}
