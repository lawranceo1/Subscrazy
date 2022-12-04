package com.example.subscrazy;


import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoadTesting {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule= new ActivityScenarioRule<MainActivity>(MainActivity.class);


    @Test
    public void loadTest(){

        for(int i = 0;i<100;i++){
            addSubs(i);
        }
    }
    public void addSubs(int i){
        onView(withId(R.id.button_first)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("A"+i), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editText_price)).perform(typeText("9.9"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editText_date)).perform(replaceText("12/11/2022"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.button_save)).perform(click());

    }
}
