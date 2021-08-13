package com.example.sawariapatkalinsewa

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class TestBusinessAdd {
    @get:Rule
    val testRule= ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun testLoginUI(){
       onView(ViewMatchers.withId(R.id.rdomechanic))
                .perform(ViewActions.click())
      onView(ViewMatchers.withId(R.id.email))
                .perform(ViewActions.typeText("Hsisisj"))
        onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText("12345"))

       closeSoftKeyboard()

       onView(ViewMatchers.withId(R.id.buttonlogin))
                .perform(ViewActions.click())

        Thread.sleep(1000)
    }

    @Test
    fun testBusiness(){
        testLoginUI()
        onView(ViewMatchers.withId(R.id.cvbusiness))
                .perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.etFullName))
                .perform(ViewActions.typeText("Rammotors"))
        onView(ViewMatchers.withId(R.id.etAge))
                .perform(ViewActions.typeText("841558"))

        onView(ViewMatchers.withId(R.id.rdoMale))
                .perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.btnaddress))
                .perform(ViewActions.click())
                .perform(ViewActions.click())

        onView(ViewMatchers.withId(R.id.btnSave))
                .perform(scrollTo())
                .perform(ViewActions.click())


    }
}