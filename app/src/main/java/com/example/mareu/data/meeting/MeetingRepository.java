package com.example.mareu.data.meeting;

import com.example.mareu.data.room.Room;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for meeting management
 */
public class MeetingRepository {

    private long mNextMeetingId = 1;
    private final List<Meeting> mMeetingList;

    public MeetingRepository() {
        mMeetingList = new ArrayList<>();
    }

    public MeetingRepository(List<Meeting> meetingList) {
        mMeetingList = meetingList;
    }

    /**
     * Get all meetings
     *
     * @return List of {@link Meeting}
     */
    public List<Meeting> getMeetingList() {
        return new ArrayList<>(mMeetingList);
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
        Meeting meetingCreated = new Meeting(mNextMeetingId, time, room, subject, participantList);
        mMeetingList.add(meetingCreated);
        mNextMeetingId++;
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
