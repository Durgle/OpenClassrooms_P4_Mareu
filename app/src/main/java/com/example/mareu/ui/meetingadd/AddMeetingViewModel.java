package com.example.mareu.ui.meetingadd;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.data.meeting.MeetingRepository;
import com.example.mareu.data.room.Room;
import com.example.mareu.data.room.RoomRepository;

import java.util.List;

public class AddMeetingViewModel extends ViewModel {

    private final RoomRepository mRoomRepository;
    private final MeetingRepository mMeetingRepository;

    private MutableLiveData<Boolean> meetingCreated = new MutableLiveData<>();

    public AddMeetingViewModel(MeetingRepository meetingRepository, RoomRepository roomRepository) {
        this.mMeetingRepository = meetingRepository;
        this.mRoomRepository = roomRepository;
    }

    public List<Room> getRooms() {
        return mRoomRepository.getRooms();
    }

    public void save(MeetingAddState meetingAddState) {

        if(meetingAddState.validData()) {
            this.mMeetingRepository.add(
                    meetingAddState.getTime(),
                    meetingAddState.getRoom(),
                    meetingAddState.getSubject(),
                    meetingAddState.getParticipants()
            );
            meetingCreated.postValue(true);
        } else {
            meetingCreated.postValue(false);
        }
    }

    public LiveData<Boolean> isMeetingCreated() {
        return meetingCreated;
    }

}
