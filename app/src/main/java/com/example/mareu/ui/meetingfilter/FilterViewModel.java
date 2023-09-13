package com.example.mareu.ui.meetingfilter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.mareu.data.room.Room;
import com.example.mareu.data.room.RoomRepository;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FilterViewModel extends ViewModel {

    private final RoomRepository mRoomRepository;
    private final MutableLiveData<LocalTime> mSelectedTime = new MutableLiveData<>();
    private final LiveData<String> mFormattedTime  = TimeFormatter.formatTime(mSelectedTime);
    private final MutableLiveData<Room> mSelectedRoom = new MutableLiveData<>();

    public FilterViewModel(RoomRepository roomRepository) {
        this.mRoomRepository = roomRepository;
    }

    public List<Room> getRooms() {
        return mRoomRepository.getRooms();
    }

    public void onSelectedTime(int hours, int min){
        mSelectedTime.setValue(LocalTime.of(hours,min));
    }

    public void onSelectedRoom(int position){
        mSelectedRoom.setValue(mRoomRepository.getRooms().get(position));
    }

    public void clearFilters() {
        mSelectedRoom.setValue(null);
        //mSelectedTime.setValue(null);
    }

    public MutableLiveData<LocalTime> getSelectedTime() {
        return mSelectedTime;
    }

    public LiveData<String> getFormattedSelectedTime() {
        return mFormattedTime;
    }

    public MutableLiveData<Room> getSelectedRoom() {
        return mSelectedRoom;
    }

    public static class TimeFormatter {
        public static LiveData<String> formatTime(LiveData<LocalTime> timeLiveData) {
            return Transformations.map(timeLiveData, time -> time.format(DateTimeFormatter.ofPattern("HH:mm")));
        }
    }
}
