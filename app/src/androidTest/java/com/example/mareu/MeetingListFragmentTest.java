package com.example.mareu;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsRule;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.mareu.data.meeting.MeetingBank;
import com.example.mareu.utils.DeleteViewAction;
import com.example.mareu.utils.RecyclerViewItemAssertion;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.stream.Collectors;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MeetingListFragmentTest {

    private static final int ITEMS_COUNT = 5;

    @Rule
    public IntentsRule intentsRule = new IntentsRule();

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
    }

    @Test
    public void testMeetingListDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.meetingList))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void meetingListShouldHaveDefaultMeeting() {

        Espresso.onView(ViewMatchers.withId(R.id.meetingList))
                .check(withItemCount(ITEMS_COUNT));
    }

    @Test
    public void meetingListDeleteAction() throws InterruptedException {

        Espresso.onView(withId(R.id.meetingList)).check(withItemCount(ITEMS_COUNT));
        Espresso.onView(withId(R.id.meetingList))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        Thread.sleep(500);
        Espresso.onView(withId(R.id.meetingList)).check(withItemCount(ITEMS_COUNT - 1));
    }

    @Test
    public void meetingListShouldStartAddFragment() {
        Espresso.onView(withId(R.id.add_meeting))
                .perform(click());
        Espresso.onView(withId(R.id.add_meeting_layout))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void meetingListShouldOpenFilterDialog() {
        Espresso.onView(withId(R.id.filter_icon))
                .perform(click());
        Espresso.onView(withId(R.id.dialog_layout))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void meetingListShouldFilter() {

        Espresso.onView(withId(R.id.filter_icon))
                .perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.dialog_field_time))
                .perform(ViewActions.typeText("12:59"));
        Espresso.onView(ViewMatchers.withId(com.google.android.material.R.id.material_timepicker_ok_button))
                .perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.dialog_field_room))
                .perform(click());
        Espresso.onView(withText("Salle 1")).inRoot(isPlatformPopup()).perform(click());
        Espresso.onView(ViewMatchers.withId(android.R.id.button1))
                .perform(click());
        Espresso.onView(withId(R.id.meetingList))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withId(R.id.meetingList)).check(withItemCount(0));

    }

    @Test
    public void meetingListShouldDisplayMeeting() {
        Espresso.onView(withId(R.id.meetingList))
                .check(
                        new RecyclerViewItemAssertion(
                                0,
                                R.id.meeting_item_title,
                                withText(MeetingBank.getInstance().getMeetings().get(0).getFormattedSubject())
                        )
                );
        Espresso.onView(withId(R.id.meetingList))
                .check(
                        new RecyclerViewItemAssertion(
                                0,
                                R.id.meeting_item_participants,
                                withText(
                                        MeetingBank.getInstance().getMeetings().get(0).getParticipantList()
                                                .stream()
                                                .map(String::valueOf)
                                                .collect(Collectors.joining(","))
                                )
                        )
                );
    }

    @Test
    public void addMeetingShouldAddMeeting() throws InterruptedException {
        Espresso.onView(withId(R.id.add_meeting))
                .perform(click());
        Espresso.onView(withId(R.id.add_meeting_field_subject))
                .perform(ViewActions.typeText("My test subject"));
        Espresso.onView(withId(R.id.add_meeting_field_time))
                .perform(ViewActions.typeText("12:00"));
        Espresso.onView(withId(com.google.android.material.R.id.material_timepicker_ok_button))
                .perform(ViewActions.click());
        Espresso.onView(withId(R.id.add_meeting_field_participants))
                .perform(ViewActions.typeText("email1@test.com;email2@test.com"));
        Espresso.onView(withId(R.id.add_meeting_field_room))
                .perform(ViewActions.click());
        Espresso.onView(withText("Salle 1"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(ViewActions.click());
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.add_meeting_submit))
                .perform(ViewActions.click());
        Espresso.onView(withId(R.id.meetingList))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withId(R.id.meetingList)).check(withItemCount(ITEMS_COUNT + 1));

    }

}