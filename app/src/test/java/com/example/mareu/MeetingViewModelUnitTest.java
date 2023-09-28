package com.example.mareu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.mareu.data.filter.FilterRepository;
import com.example.mareu.data.meeting.Meeting;
import com.example.mareu.data.meeting.MeetingBank;
import com.example.mareu.data.meeting.MeetingRepository;
import com.example.mareu.data.room.Room;
import com.example.mareu.data.room.RoomBank;
import com.example.mareu.ui.meetingfilter.FilterState;
import com.example.mareu.ui.meetingslist.MeetingViewModel;
import com.example.mareu.utils.LiveDataTestUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.List;


@RunWith(JUnit4.class)
public class MeetingViewModelUnitTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    private List<Meeting> mFakeMeetingList;
    private MeetingRepository mMockedMeetingRepository;

    @Before
    public void setup() throws InterruptedException {

        mFakeMeetingList = MeetingBank.getInstance().getMeetingForTest();
        mMockedMeetingRepository = Mockito.mock(MeetingRepository.class);
        Mockito.when(mMockedMeetingRepository.getMeetingList())
                .thenReturn(mFakeMeetingList);
    }

    @Test
    public void initListWithoutFilter() throws InterruptedException {
        MeetingViewModel meetingViewModel = new MeetingViewModel(mMockedMeetingRepository, new FilterRepository());
        meetingViewModel.initList();
        List<Meeting> meetingList = LiveDataTestUtil.getOrAwaitValue(meetingViewModel.getMeetingList());
        assertEquals(meetingList, mFakeMeetingList);
    }

    @Test
    public void initListWithRoomFilter() throws InterruptedException {
        FilterRepository filterRepository = Mockito.mock(FilterRepository.class);
        Room roomFilter = RoomBank.getInstance().getRoomById(2);
        Mockito.when(filterRepository.getFilterState())
                .thenReturn(new MutableLiveData<>(new FilterState(null, roomFilter)));
        MeetingViewModel meetingViewModel = new MeetingViewModel(mMockedMeetingRepository, filterRepository);

        meetingViewModel.initList();
        List<Meeting> meetingList = LiveDataTestUtil.getOrAwaitValue(meetingViewModel.getMeetingList());
        boolean hasRoomTwo = meetingList.stream()
                .allMatch(meeting -> meeting.getRoom() == roomFilter);
        assertTrue(hasRoomTwo);
    }

    @Test
    public void initListWithTimeFilter() throws InterruptedException {
        FilterRepository filterRepository = Mockito.mock(FilterRepository.class);
        LocalTime timeFilter = LocalTime.of(0, 0);
        Mockito.when(filterRepository.getFilterState())
                .thenReturn(new MutableLiveData<>(new FilterState(timeFilter, null)));
        MeetingViewModel meetingViewModel = new MeetingViewModel(mMockedMeetingRepository, filterRepository);

        meetingViewModel.initList();
        List<Meeting> meetingList = LiveDataTestUtil.getOrAwaitValue(meetingViewModel.getMeetingList());
        boolean hasTime = meetingList.stream()
                .allMatch(meeting -> meeting.getTime() == timeFilter);
        assertTrue(hasTime);
    }

    @Test
    public void initListWithTimeAndRoomFilter() throws InterruptedException {
        FilterRepository filterRepository = Mockito.mock(FilterRepository.class);
        LocalTime timeFilter = LocalTime.of(0, 0);
        Room roomFilter = RoomBank.getInstance().getRoomById(5);
        Mockito.when(filterRepository.getFilterState())
                .thenReturn(new MutableLiveData<>(new FilterState(timeFilter, roomFilter)));
        MeetingViewModel meetingViewModel = new MeetingViewModel(mMockedMeetingRepository, filterRepository);

        meetingViewModel.initList();
        List<Meeting> meetingList = LiveDataTestUtil.getOrAwaitValue(meetingViewModel.getMeetingList());
        assertTrue(meetingList.isEmpty());
    }

    @Test
    public void deleteMeetingWithSuccess() {
        doNothing().when(mMockedMeetingRepository).delete(anyLong());
        MeetingViewModel meetingViewModel = new MeetingViewModel(mMockedMeetingRepository, new FilterRepository());
        Meeting meetingToDelete = mFakeMeetingList.get(2);
        meetingViewModel.deleteMeeting(meetingToDelete.getId());
        verify(mMockedMeetingRepository).delete(meetingToDelete.getId());
    }

}
