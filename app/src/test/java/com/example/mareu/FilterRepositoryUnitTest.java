package com.example.mareu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.mareu.data.filter.FilterRepository;
import com.example.mareu.data.room.Room;
import com.example.mareu.ui.meetingfilter.FilterState;
import com.example.mareu.utils.LiveDataTestUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalTime;


@RunWith(JUnit4.class)
public class FilterRepositoryUnitTest {

    private FilterRepository mFilterRepository;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        mFilterRepository = new FilterRepository();
    }

    @Test
    public void setFilterStateWithSuccess() throws InterruptedException {
        LocalTime time = LocalTime.now();
        Room room = new Room(1, "Room Test", R.color.purple);
        FilterState newFilterState = new FilterState(time, room);
        mFilterRepository.setFilterState(newFilterState);

        FilterState filterState = LiveDataTestUtil.getOrAwaitValue(mFilterRepository.getFilterState());
        assertEquals(filterState.getTime(), time);
        assertEquals(filterState.getRoom(), room);
    }

    @Test
    public void clearFiltersWithSuccess() throws InterruptedException {
        LocalTime time = LocalTime.now();
        Room room = new Room(1, "Room Test", R.color.purple);
        FilterState newFilterState = new FilterState(time, room);
        mFilterRepository.setFilterState(newFilterState);
        FilterState filterState = LiveDataTestUtil.getOrAwaitValue(mFilterRepository.getFilterState());
        assertNotNull(filterState);
        mFilterRepository.clearFilters();
        FilterState filterStateAfterClear = LiveDataTestUtil.getOrAwaitValue(mFilterRepository.getFilterState());
        assertNull(filterStateAfterClear);
    }

}
