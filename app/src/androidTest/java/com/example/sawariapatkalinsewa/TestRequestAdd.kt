package com.example.sawariapatkalinsewa

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@LargeTest
@RunWith(JUnit4::class)
class TestRequestAdd {
    @get:Rule
    val testRule= ActivityScenarioRule(LoginActivity::class.java)


    @Test
    fun testLoginUI(){
        Espresso.onView(ViewMatchers.withId(R.id.rdocustomer))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(ViewActions.typeText("mansur"))
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText("12345"))

        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.buttonlogin))
                .perform(ViewActions.click())

        Thread.sleep(1000)
    }
    @Test
    fun testRequests(){
        testLoginUI()
        Espresso.onView(ViewMatchers.withId(R.id.cvflatetire))
                .perform(ViewActions.click())
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.btnraddress))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnrequest))
                .perform(ViewActions.click())


    }
}