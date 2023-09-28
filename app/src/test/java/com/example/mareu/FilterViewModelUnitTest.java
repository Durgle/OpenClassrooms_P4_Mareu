package com.example.mareu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.mareu.data.filter.FilterRepository;
import com.example.mareu.data.room.Room;
import com.example.mareu.data.room.RoomBank;
import com.example.mareu.data.room.RoomRepository;
import com.example.mareu.ui.meetingfilter.FilterState;
import com.example.mareu.ui.meetingfilter.FilterViewModel;
import com.example.mareu.utils.LiveDataTestUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RunWith(JUnit4.class)
public class FilterViewModelUnitTest {

    private FilterRepository mFilterRepository;
    private RoomRepository mRoomRepository;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        mFilterRepository = new FilterRepository();
        mRoomRepository = Mockito.mock(RoomRepository.class);
        Mockito.when(mRoomRepository.getRooms())
                .thenReturn(RoomBank.getInstance().getRooms());
    }

    @Test
    public void getRoomsWithSuccess() {
        FilterViewModel filterViewModel = new FilterViewModel(mRoomRepository, mFilterRepository);
        List<Room> rooms = filterViewModel.getRooms();
        assertEquals(rooms, mRoomRepository.getRooms());
    }

    @Test
    public void clearFiltersWithSuccess() throws InterruptedException {
        mFilterRepository.setFilterState(new FilterState(LocalTime.now(), new Room(50, "test", R.color.purple)));
        FilterViewModel filterViewModel = new FilterViewModel(mRoomRepository, mFilterRepository);
        filterViewModel.clearFilters();
        FilterState filterState = LiveDataTestUtil.getOrAwaitValue(mFilterRepository.getFilterState());
        assertNull(filterState);
    }

    @Test
    public void onSelectedTime() throws InterruptedException {
        FilterViewModel filterViewModel = new FilterViewModel(mRoomRepository, mFilterRepository);
        int hours = 14;
        int min = 30;
        filterViewModel.onSelectedTime(hours, min);

        String timeFormatted = LiveDataTestUtil.getOrAwaitValue(filterViewModel.getFormattedSelectedTime());
        assertEquals(timeFormatted, LocalTime.of(hours, min).format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    @Test
    public void onSelectedRoom() throws InterruptedException {
        FilterViewModel filterViewModel = new FilterViewModel(mRoomRepository, mFilterRepository);
        Room room = new Room(50, "test", R.color.purple);
        filterViewModel.onSelectedRoom(2);
        Room roomSelected = mRoomRepository.getRooms().get(2);

        String roomName = LiveDataTestUtil.getOrAwaitValue(filterViewModel.getSelectedRoomText());
        assertEquals(roomName, roomSelected.getName());
    }

    @Test
    public void confirmFiltersWithSuccess() throws InterruptedException {
        mFilterRepository.setFilterState(new FilterState(LocalTime.now(), new Room(50, "test", R.color.purple)));
        FilterViewModel filterViewModel = new FilterViewModel(mRoomRepository, mFilterRepository);
        int hours = 14;
        int min = 30;
        Room roomSelected = mRoomRepository.getRooms().get(2);
        filterViewModel.onSelectedTime(hours, min);
        filterViewModel.onSelectedRoom(2);
        filterViewModel.confirmFilters();
        FilterState filterState = LiveDataTestUtil.getOrAwaitValue(mFilterRepository.getFilterState());
        assertEquals(filterState.getRoom(), roomSelected);
        assertEquals(filterState.getTime(), LocalTime.of(hours, min));
    }

}
