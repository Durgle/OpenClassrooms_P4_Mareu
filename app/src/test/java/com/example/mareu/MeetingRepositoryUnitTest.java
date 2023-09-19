package com.example.mareu;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.mareu.data.meeting.Meeting;
import com.example.mareu.data.meeting.MeetingRepository;
import com.example.mareu.data.room.Room;
import com.example.mareu.data.room.RoomBank;
import com.example.mareu.data.room.RoomRepository;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@RunWith(JUnit4.class)
public class MeetingRepositoryUnitTest {

    private MeetingRepository mMeetingRepository;
    private RoomRepository mRoomRepository;
    private List<Meeting> mFakeMeetings;

    public static final long MEETING_ID = 1;

    @Before
    public void setup() {

        mRoomRepository = new RoomRepository(RoomBank.getInstance());
        mMeetingRepository = new MeetingRepository();
        mFakeMeetings = mMeetingRepository.addFakeData();
    }

    @Test
    public void getMeetingListWithSuccess() {
        List<Meeting> meetingList = mMeetingRepository.getMeetingList();
        assertThat(meetingList, IsIterableContainingInAnyOrder.containsInAnyOrder(mFakeMeetings.toArray()));
    }

    @Test
    public void addMeetingWithSuccess() {
        LocalTime localTime = LocalTime.now();
        Room room = mRoomRepository.getRooms().get(0);
        String subject = "Test Meeting Subject";
        List<String> participant = new ArrayList<>();
        participant.add("ParticipantOne@email.com");
        participant.add("ParticipantTwo@email.com");

        Meeting meetingAdded = mMeetingRepository.add(localTime, room, subject, participant);
        assertTrue(mMeetingRepository.getMeetingList().contains(meetingAdded));
    }

    @Test
    public void getMeetingByIdWithSuccess() {
        Meeting meeting = mMeetingRepository.getMeetingById(MEETING_ID);
        assertEquals(meeting.getId(), MEETING_ID);
    }

    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = mMeetingRepository.getMeetingList().get(0);
        mMeetingRepository.delete(meetingToDelete.getId());
        assertFalse(mMeetingRepository.getMeetingList().contains(meetingToDelete));
    }

}
