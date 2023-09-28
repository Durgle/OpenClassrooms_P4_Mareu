package com.example.mareu;

import androidx.fragment.app.FragmentTransaction;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.rule.IntentsRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.mareu.ui.meetingadd.MeetingAddFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AddMeetingFragmentTest {

    private static final int ITEMS_COUNT = 5;

    @Rule
    public IntentsRule intentsRule = new IntentsRule();

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        ActivityScenario<MainActivity> scenario = activityRule.getScenario();
        scenario.onActivity(activity -> {
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_container, new MeetingAddFragment());
            transaction.commit();
        });
    }

    @Test
    public void addMeetingShouldDisplayError() {
        Espresso.onView(ViewMatchers.withId(R.id.add_meeting_field_subject))
                .perform(ViewActions.typeText("My test subject"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.add_meeting_submit))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(com.google.android.material.R.id.snackbar_text))
                .check(ViewAssertions.matches(ViewMatchers.withText(R.string.add_meeting_time_error)));
        Espresso.onView(ViewMatchers.withId(R.id.add_meeting_layout))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}