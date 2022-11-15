package com.example.subscrazy;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class deleteSubscription_Test {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    //This test checks if a subscription can be deleted from the app
    @Test
    public void deleteSubscription_Test() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.button_first), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_main),
                                        0),
                                0),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editText_name),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_main),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Amazon"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editText_price),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_main),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("11.99"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.editText_date),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_main),
                                        0),
                                7),
                        isDisplayed()));
        appCompatEditText3.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton2.perform(scrollTo(), click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.button_save), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_main),
                                        0),
                                8),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.idRVSubscriptions),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                2)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.buttonDelete), withText("Delete"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_main),
                                        0),
                                9),
                        isDisplayed()));
        materialButton4.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
