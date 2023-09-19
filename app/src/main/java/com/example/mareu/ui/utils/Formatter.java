package com.example.mareu.ui.utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.mareu.ui.meetingfilter.FilterState;

import java.time.format.DateTimeFormatter;

public class Formatter {

    public static LiveData<String> formatTime(LiveData<FilterState> filterStateLiveData) {
        return Transformations.map(
                filterStateLiveData,
                filterState -> filterState.getTime() != null ?
                        filterState.getTime().format(DateTimeFormatter.ofPattern("HH:mm")) : null
        );
    }

    public static LiveData<String> formatRoom(LiveData<FilterState> filterStateLiveData) {
        return Transformations.map(
                filterStateLiveData,
                filterState -> filterState.getRoom() != null ? filterState.getRoom().getName() : null
        );
    }
}
