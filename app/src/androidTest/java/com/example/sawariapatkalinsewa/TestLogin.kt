package com.example.sawariapatkalinsewa

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class TestLogin {

    @get:Rule
    val testRule=ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun testLoginUI(){
        onView(withId(R.id.rdocustomer))
                .perform(click())
        onView(withId(R.id.email))
                .perform(typeText("mansur"))
        onView(withId(R.id.password))
                .perform(typeText("12345"))

        closeSoftKeyboard()

        onView(withId(R.id.buttonlogin))
                .perform(click())

        Thread.sleep(1000)
    }
}