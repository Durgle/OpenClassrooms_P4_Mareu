package com.example.mareu.injection;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.mareu.data.filter.FilterRepository;
import com.example.mareu.data.meeting.MeetingBank;
import com.example.mareu.data.meeting.MeetingRepository;
import com.example.mareu.data.room.RoomBank;
import com.example.mareu.data.room.RoomRepository;
import com.example.mareu.ui.meetingadd.AddMeetingViewModel;
import com.example.mareu.ui.meetingfilter.FilterViewModel;
import com.example.mareu.ui.meetingslist.MeetingViewModel;

import org.jetbrains.annotations.NotNull;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final RoomRepository mRoomRepository;
    private final MeetingRepository mMeetingRepository;
    private final FilterRepository mFilterRepository;
    private static ViewModelFactory factory;

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory();
                }
            }
        }
        return factory;
    }

    private ViewModelFactory() {
        RoomBank roomBank = RoomBank.getInstance();
        MeetingBank meetingBank = MeetingBank.getInstance();
        this.mRoomRepository = new RoomRepository(roomBank.getRooms());
        this.mMeetingRepository = new MeetingRepository(meetingBank.getMeetings());
        this.mFilterRepository = new FilterRepository();
    }

    @SuppressWarnings("unchecked")
    @Override
    @NotNull
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MeetingViewModel.class)) {
            return (T) new MeetingViewModel(this.mMeetingRepository, this.mFilterRepository);
        }
        if (modelClass.isAssignableFrom(AddMeetingViewModel.class)) {
            return (T) new AddMeetingViewModel(this.mMeetingRepository, this.mRoomRepository);
        }
        if (modelClass.isAssignableFrom(FilterViewModel.class)) {
            return (T) new FilterViewModel(this.mRoomRepository, this.mFilterRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass, @NonNull CreationExtras extras) {
        return ViewModelProvider.Factory.super.create(modelClass, extras);
    }
}