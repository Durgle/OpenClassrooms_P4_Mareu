package com.example.mareu.data.room;

import java.util.List;

public class RoomRepository {

    private final List<Room> mRooms;

    /**
     * RoomRepository constructor
     *
     * @param rooms List of {@link Room}
     */
    public RoomRepository(List<Room> rooms) {
        this.mRooms = rooms;
    }

    /**
     * Get all rooms
     *
     * @return List of {@link Room}
     */
    public List<Room> getRooms() {
        return mRooms;
    }
}
