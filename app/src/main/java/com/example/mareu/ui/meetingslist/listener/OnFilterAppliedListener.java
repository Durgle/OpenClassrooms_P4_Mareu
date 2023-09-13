package com.example.mareu.ui.meetingslist.listener;

import androidx.annotation.NonNull;

import com.example.mareu.data.room.Room;

import java.time.LocalTime;

public interface OnFilterAppliedListener {

    void onFilterApplied(LocalTime time, Room room);
}
