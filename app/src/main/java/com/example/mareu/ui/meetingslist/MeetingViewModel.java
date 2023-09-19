package com.example.mareu.ui.meetingslist;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.data.filter.FilterRepository;
import com.example.mareu.data.meeting.Meeting;
import com.example.mareu.data.meeting.MeetingRepository;
import com.example.mareu.ui.meetingfilter.FilterState;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MeetingViewModel extends ViewModel {

    private final MeetingRepository mMeetingRepository;
    private final FilterRepository mFilterRepository;
    private final MutableLiveData<List<Meeting>> mMeetingList = new MediatorLiveData<>();
    private final MediatorLiveData<List<Meeting>> mFilteredMeetingList = new MediatorLiveData<>();

    public MeetingViewModel(MeetingRepository meetingRepository, FilterRepository filterRepository) {
        this.mMeetingRepository = meetingRepository;
        this.mFilterRepository = filterRepository;

        meetingRepository.addFakeData();
        mFilteredMeetingList.addSource(
                mMeetingList, meetingList -> combine(meetingList, mFilterRepository.getFilterState().getValue())
        );
        mFilteredMeetingList.addSource(
                filterRepository.getFilterState(), filterState -> combine(mMeetingList.getValue(), filterState)
        );
    }

    public void initList() {
        this.mMeetingList.setValue(mMeetingRepository.getMeetingList());
    }

    private void combine(@Nullable List<Meeting> meetingList, @Nullable FilterState filterState) {

        if (meetingList != null && filterState != null) {

            Predicate<Meeting> doNotFilter = meeting -> true;
            Predicate<Meeting> timeFilter = meeting -> meeting.getTime().equals(filterState.getTime());
            Predicate<Meeting> roomFilter = meeting -> meeting.getRoom().getId() == filterState.getRoom().getId();

            mFilteredMeetingList.setValue(
                    meetingList.stream()
                            .filter(filterState.getTime() == null ? doNotFilter : timeFilter)
                            .filter(filterState.getRoom() == null ? doNotFilter : roomFilter)
                            .collect(Collectors.toList())
            );
        } else {
            mFilteredMeetingList.setValue(meetingList);
        }

    }

    public void deleteMeeting(long meetingId) {
        mMeetingRepository.delete(meetingId);
        initList();
    }

    public LiveData<List<Meeting>> getMeetingList() {
        return mFilteredMeetingList;
    }

}
