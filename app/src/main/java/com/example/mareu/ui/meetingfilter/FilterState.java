package com.example.mareu.ui.meetingfilter;

import com.example.mareu.data.room.Room;

import java.time.LocalTime;

public class FilterState {

    private final LocalTime time;
    private final Room room;

    public FilterState(LocalTime time, Room room) {
        this.time = time;
        this.room = room;
    }

    public LocalTime getTime() {
        return time;
    }

    public Room getRoom() {
        return room;
    }
}
