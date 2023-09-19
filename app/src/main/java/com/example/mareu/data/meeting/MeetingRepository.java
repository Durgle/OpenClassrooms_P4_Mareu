package com.example.mareu.data.meeting;

import com.example.mareu.data.room.Room;
import com.example.mareu.data.room.RoomBank;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MeetingRepository {

    private long mLastMeetingId = 1;
    private final List<Meeting> mMeetingList = new ArrayList<>();

    /**
     * Get all meetings
     *
     * @return List of {@link Meeting}
     */
    public List<Meeting> getMeetingList() {
        return new ArrayList<>(mMeetingList);
    }

    /**
     * Add fake {@link Meeting}
     *
     * @return List of {@link Meeting} generated
     */
    public List<Meeting> addFakeData(int nbElement) {

        List<Meeting> fakeMeetings = new ArrayList<>();
        for (int i = 0; i < nbElement; i++) {
            Random r = new Random();
            int hour = r.nextInt(11) + 1;
            int min = r.nextInt(59);

            Meeting meeting = new Meeting(
                    mLastMeetingId, LocalTime.of(hour, min),
                    RoomBank.getRandomRoom(),
                    String.format("%s %s", "Meeting", mLastMeetingId),
                    Arrays.asList("user1@email.com", "user2@email.com", "user3@email.com")
            );
            mMeetingList.add(meeting);
            fakeMeetings.add(meeting);
            mLastMeetingId++;
        }

        return fakeMeetings;

    }

    /**
     * Add fake {@link Meeting}
     */
    public List<Meeting> addFakeData() {
        return addFakeData(10);
    }

    /**
     * Add new {@link Meeting}
     *
     * @param time            Meeting time
     * @param room            Meeting room
     * @param subject         Meeting subject
     * @param participantList List of all participants
     * @return Meeting added
     */
    public Meeting add(LocalTime time, Room room, String subject, List<String> participantList) {
        Meeting meetingCreated = new Meeting(mLastMeetingId, time, room, subject, participantList);
        mMeetingList.add(meetingCreated);
        mLastMeetingId++;
        return meetingCreated;
    }

    /**
     * Get the {@link Meeting} by his id
     *
     * @param id Meeting id
     * @return {@link Meeting}
     */
    public Meeting getMeetingById(long id) {

        for (Meeting meeting : mMeetingList) {
            if (meeting.getId() == id) {
                return meeting;
            }
        }

        return null;
    }

    /**
     * Delete the given {@link Meeting}
     *
     * @param meetingId Meeting Id
     */
    public void delete(long meetingId) {
        Meeting meeting = this.getMeetingById(meetingId);
        if (meeting != null) {
            mMeetingList.remove(meeting);
        }
    }
}
