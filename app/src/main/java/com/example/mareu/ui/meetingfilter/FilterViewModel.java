package com.example.mareu.ui.meetingfilter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.data.filter.FilterRepository;
import com.example.mareu.data.room.Room;
import com.example.mareu.data.room.RoomRepository;
import com.example.mareu.ui.utils.Formatter;

import java.time.LocalTime;
import java.util.List;

public class FilterViewModel extends ViewModel {

    private final RoomRepository mRoomRepository;
    private final FilterRepository mFilterRepository;
    private final MutableLiveData<FilterState> mFilterState = new MutableLiveData<>();
    private final LiveData<String> mFormattedTime = Formatter.formatTime(mFilterState);
    private final LiveData<String> mFormattedRoomText = Formatter.formatRoom(mFilterState);

    public FilterViewModel(RoomRepository roomRepository, FilterRepository filterRepository) {
        this.mRoomRepository = roomRepository;
        this.mFilterRepository = filterRepository;
        FilterState filterState = mFilterRepository.getFilterState().getValue();
        if (filterState != null) {
            mFilterState.setValue(new FilterState(filterState.getTime(), filterState.getRoom()));
        }
    }

    public List<Room> getRooms() {
        return mRoomRepository.getRooms();
    }

    public void onSelectedTime(int hours, int min) {
        mFilterState.setValue(new FilterState(
                LocalTime.of(hours, min),
                mFilterState.getValue() != null ? mFilterState.getValue().getRoom() : null
        ));
    }

    public void onSelectedRoom(int position) {
        mFilterState.setValue(new FilterState(
                mFilterState.getValue() != null ? mFilterState.getValue().getTime() : null,
                mRoomRepository.getRooms().get(position)
        ));
    }

    public void clearFilters() {
        mFilterRepository.clearFilters();
    }

    public MutableLiveData<FilterState> getFilterState() {
        return mFilterRepository.getFilterState();
    }

    public LiveData<String> getFormattedSelectedTime() {
        return mFormattedTime;
    }

    public LiveData<String> getSelectedRoomText() {
        return mFormattedRoomText;
    }

    public void confirmFilters() {
        mFilterRepository.setFilterState(mFilterState.getValue());
    }

}
