package com.example.mareu.data.room;

import java.util.List;

public class RoomRepository {
    private final RoomBank mRoomBank;

    /**
     * RoomRepository constructor
     *
     * @param roomBank Room bank
     */
    public RoomRepository(RoomBank roomBank) {
        this.mRoomBank = roomBank;
    }

    /**
     * Get all rooms
     *
     * @return List of {@link Room}
     */
    public List<Room> getRooms() {
        return this.mRoomBank.getRooms();
    }
}
