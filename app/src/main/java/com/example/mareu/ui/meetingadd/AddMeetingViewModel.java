package com.example.mareu.ui.meetingadd;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.mareu.data.meeting.MeetingRepository;
import com.example.mareu.data.room.Room;
import com.example.mareu.data.room.RoomRepository;
import com.example.mareu.ui.utils.MessageTypeEnum;
import com.example.mareu.ui.utils.SingleLiveEvent;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AddMeetingViewModel extends ViewModel {

    private final RoomRepository mRoomRepository;
    private final MeetingRepository mMeetingRepository;
    private final MutableLiveData<LocalTime> mSelectedTime = new MutableLiveData<>();
    private final MutableLiveData<Room> mSelectedRoom = new MutableLiveData<>();
    private final SingleLiveEvent<MessageTypeEnum> mMessage = new SingleLiveEvent<>();
    private final MutableLiveData<String> mSubject = new MutableLiveData<>();
    private final MutableLiveData<List<String>> mParticipant = new MutableLiveData<>();

    public AddMeetingViewModel(MeetingRepository meetingRepository, RoomRepository roomRepository) {
        this.mMeetingRepository = meetingRepository;
        this.mRoomRepository = roomRepository;
    }

    public List<Room> getRooms() {
        return mRoomRepository.getRooms();
    }

    public void save() {

        if (this.validateData()) {
            this.mMeetingRepository.add(
                    mSelectedTime.getValue(),
                    mSelectedRoom.getValue(),
                    mSubject.getValue(),
                    mParticipant.getValue()
            );
            mMessage.setValue(MessageTypeEnum.SUCCESS);
        }
    }

    public SingleLiveEvent<MessageTypeEnum> getMessage() {
        return mMessage;
    }

    public void onSelectedTime(int hours, int min) {
        mSelectedTime.setValue(LocalTime.of(hours, min));
    }

    public void onSelectedRoom(int position) {
        mSelectedRoom.setValue(mRoomRepository.getRooms().get(position));
    }

    public void onSubjectChanged(String subject) {
        mSubject.setValue(subject);
    }

    public void onParticipantChanged(String participants) {
        mParticipant.setValue(
                Arrays.asList(String.valueOf(participants).split(","))
        );
    }

    public LiveData<String> getSelectedTime() {
        return Transformations.map(mSelectedTime, time -> time.format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    private boolean validateData() {
        if (Objects.equals(mSubject.getValue(), null) || Objects.requireNonNull(mSubject.getValue()).isEmpty()) {
            mMessage.setValue(MessageTypeEnum.ERROR_SUBJECT);
            return false;
        }
        if (mSelectedTime.getValue() == null) {
            mMessage.setValue(MessageTypeEnum.ERROR_TIME);
            return false;
        }
        if (mSelectedRoom.getValue() == null) {
            mMessage.setValue(MessageTypeEnum.ERROR_ROOM);
            return false;
        }
        if (Objects.equals(mParticipant.getValue(), null) || Objects.requireNonNull(mParticipant.getValue()).isEmpty()) {
            mMessage.setValue(MessageTypeEnum.ERROR_PARTICIPANT);
            return false;
        }

        return true;
    }

}
