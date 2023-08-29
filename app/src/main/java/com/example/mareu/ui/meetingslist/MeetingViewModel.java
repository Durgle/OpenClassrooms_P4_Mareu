package com.example.mareu.ui.meetingslist;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.data.meeting.Meeting;
import com.example.mareu.data.meeting.MeetingRepository;

import java.util.List;

public class MeetingViewModel  extends ViewModel {

    private MeetingRepository mMeetingRepository;

    MutableLiveData<List<Meeting>> mMeetingList = new MutableLiveData<>();

    public MeetingViewModel(MeetingRepository meetingRepository) {
        this.mMeetingRepository = meetingRepository;
        this.mMeetingRepository.addFakeData();
    }

    public void initList(){
        this.mMeetingList.setValue(mMeetingRepository.getMeetingList());
    }

}
