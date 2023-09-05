package com.example.mareu.ui.meetingadd;

import com.example.mareu.data.room.Room;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class MeetingAddState {

    private final LocalTime mTime;
    private final String mSubject;
    private final Room mRoom;
    private final String mParticipants;

    public MeetingAddState(LocalTime time, String subject, Room room, String participants) {
        mTime = time;
        mSubject = subject;
        mRoom = room;
        mParticipants = participants;
    }

    public LocalTime getTime() {
        return mTime;
    }

    public String getSubject() {
        return mSubject;
    }

    public Room getRoom() {
        return mRoom;
    }

    public List<String> getParticipants() {
        return this.formatParticipants();
    }

    private List<String> formatParticipants(){
        String value = String.valueOf(mParticipants);
        return Arrays.asList(value.split(","));

    }

    public boolean validData() {
        return mTime != null &&
                mRoom != null &&
                mParticipants != null &&
                mSubject != null &&
                !mSubject.isEmpty();
    }
}