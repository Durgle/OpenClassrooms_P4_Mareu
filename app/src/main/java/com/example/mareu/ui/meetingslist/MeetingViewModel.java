package com.example.mareu.ui.meetingslist;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.mareu.data.meeting.Meeting;
import com.example.mareu.data.meeting.MeetingRepository;
import com.example.mareu.data.room.Room;
import com.example.mareu.data.room.RoomRepository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MeetingViewModel  extends ViewModel {

    private final MeetingRepository mMeetingRepository;
    private final MutableLiveData<List<Meeting>> mMeetingList = new MutableLiveData<>();
    private final MutableLiveData<List<Meeting>> mFilteredMeetings = new MutableLiveData<>();
    private final MediatorLiveData<List<Meeting>> mCombinedMeetings = new MediatorLiveData<>();

    public MeetingViewModel(MeetingRepository meetingRepository) {
        this.mMeetingRepository = meetingRepository;

        meetingRepository.addFakeData();
        mCombinedMeetings.addSource(mMeetingList,new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetingList) {
                combine(meetingList, mFilteredMeetings.getValue());
            }
        });
        mCombinedMeetings.addSource(mFilteredMeetings,new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetingList) {
                combine(mMeetingList.getValue(), meetingList);
            }
        });
    }

    public void initList() {
        this.mMeetingList.setValue(mMeetingRepository.getMeetingList());
    }

    private void combine(@Nullable List<Meeting> meetingList, @Nullable List<Meeting> filteredMeetingList) {

        List<Meeting> combinedMeeting = new ArrayList<>();
        for (Meeting meeting : meetingList) {
            if(filteredMeetingList == null || filteredMeetingList.contains(meeting)){
                combinedMeeting.add(meeting);
            }
        }
        mCombinedMeetings.setValue(combinedMeeting);
    }

    public void deleteMeeting(long meetingId) {
        mMeetingRepository.delete(meetingId);
        this.mMeetingList.setValue(mMeetingRepository.getMeetingList());
    }

    public LiveData<List<Meeting>> getCombinedMeetings() {
        return mCombinedMeetings;
    }

    public void onAppliedFilter(LocalTime time, Room room) {

        if(mMeetingList.getValue() != null) {
            List<Meeting> list = mMeetingList.getValue()
                    .stream()
                    .filter(meeting -> time == null || meeting.getTime().equals(time))
                    .filter(meeting -> room == null || meeting.getRoom().getId() == room.getId())
                    .collect(Collectors.toList());

            mFilteredMeetings.setValue(list);
        }

    }

}
