package com.example.mareu;

import static org.hamcrest.MatcherAssert.assertThat;

import com.example.mareu.data.room.Room;
import com.example.mareu.data.room.RoomBank;
import com.example.mareu.data.room.RoomRepository;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;


@RunWith(JUnit4.class)
public class RoomRepositoryUnitTest {

    private RoomRepository mRepository;

    @Before
    public void setup() {
        mRepository = new RoomRepository(RoomBank.getInstance());
    }

    @Test
    public void getRoomWithSuccess() {
        List<Room> rooms = mRepository.getRooms();
        List<Room> expectedRooms = RoomBank.getInstance().getRooms();
        assertThat(rooms, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedRooms.toArray()));
    }

}
