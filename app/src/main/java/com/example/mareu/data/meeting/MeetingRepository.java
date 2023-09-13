package com.example.mareu.data.meeting;

import androidx.annotation.NonNull;

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
     */
    public void addFakeData() {

        for (int i = 1; i < 6; i++) {
            Random r = new Random();
            int hour = r.nextInt(11) + 1;
            int min = r.nextInt(59);

            mMeetingList.add(
                    new Meeting(
                            mLastMeetingId,LocalTime.of(hour,min),
                            RoomBank.getRandomRoom(),
                            String.format("%s %s", "Meeting", i),
                            Arrays.asList("user1@email.com","user2@email.com","user3@email.com")
                    )
            );
            mLastMeetingId++;
        }

    }

    /**
     * Add new {@link Meeting}
     *
     * @param time Meeting time
     * @param room Meeting room
     * @param subject Meeting subject
     * @param participantList List of all participants
     */
    public void add(LocalTime time, Room room, String subject, List<String> participantList) {
        mMeetingList.add(new Meeting(mLastMeetingId,time,room,subject,participantList));
        mLastMeetingId++;
    }

    /**
     * Get the {@link Meeting} by his id
     *
     * @param id Meeting id
     * @return {@link Meeting}
     */
    public Meeting getMeetingById(long id) {

        for (Meeting meeting : mMeetingList) {
            if(meeting.getId() == id){
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
        if(meeting != null) {
            mMeetingList.remove(meeting);
        }
    }
}
