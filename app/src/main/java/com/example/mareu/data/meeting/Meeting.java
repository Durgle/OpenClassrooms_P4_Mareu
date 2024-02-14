package com.example.mareu.data.meeting;

import androidx.annotation.NonNull;

import com.example.mareu.data.room.Room;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * Meeting entity
 */
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
    public Room getRoom() {
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

    /**
     * Create the subject for the {@link Meeting}
     *
     * @return Subject
     */
    @NonNull
    public String getFormattedSubject() {
        return String.format("%s - %s - %s",
                this.mSubject,
                this.mTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                this.mRoom.getName()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return mId == meeting.mId &&
                mTime.equals(meeting.mTime) &&
                mRoom.equals(meeting.mRoom) &&
                mSubject.equals(meeting.mSubject) &&
                mParticipantList.equals(meeting.mParticipantList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mTime, mRoom, mSubject, mParticipantList);
    }
}
