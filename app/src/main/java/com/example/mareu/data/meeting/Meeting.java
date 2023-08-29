package com.example.mareu.data.meeting;

import androidx.annotation.NonNull;

import com.example.mareu.data.room.Room;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Meeting {

    private final long mId;
    @NonNull
    private final LocalTime mTime;
    @NonNull
    private final Room mRoom;
    @NonNull
    private final String mSubject;
    @NonNull
    private final List<String> mParticipantList;

    public Meeting(
            long id,
            @NonNull LocalTime time,
            @NonNull Room room,
            @NonNull String subject,
            @NonNull List<String> participantList
    ) {
        this.mId = id;
        this.mTime = time;
        this.mRoom = room;
        this.mSubject = subject;
        this.mParticipantList = participantList;
    }

    public long getId() {
        return mId;
    }

    @NonNull
    public LocalTime getTime() {
        return mTime;
    }

    @NonNull
    public  Room getRoom() {
        return mRoom;
    }

    @NonNull
    public String getSubject() {
        return mSubject;
    }

    @NonNull
    public List<String> getParticipantList() {
        return mParticipantList;
    }

    @NonNull
    public String getFormattedSubject() {
        return String.format("%s - %s - %s",
                this.mSubject,
                this.mTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                this.mRoom.getName()
        );
    }

}
