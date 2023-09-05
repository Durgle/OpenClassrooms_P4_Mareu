package com.example.mareu.ui.meetingslist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.data.meeting.Meeting;
import com.example.mareu.data.meeting.MeetingRepository;

import java.util.List;

public class MeetingViewModel  extends ViewModel {

    private final MeetingRepository mMeetingRepository;

    public LiveData<List<Meeting>> getMeetingList() {
        return mMeetingList;
    }

    private final MutableLiveData<List<Meeting>> mMeetingList = new MutableLiveData<>();

    public MeetingViewModel(MeetingRepository meetingRepository) {
        this.mMeetingRepository = meetingRepository;
        this.mMeetingRepository.addFakeData();
    }

    public void initList(){
        this.mMeetingList.setValue(mMeetingRepository.getMeetingList());
    }

    public void deleteMeeting(long meetingId) {
        mMeetingRepository.delete(meetingId);
        this.mMeetingList.setValue(mMeetingRepository.getMeetingList());
    }

}
