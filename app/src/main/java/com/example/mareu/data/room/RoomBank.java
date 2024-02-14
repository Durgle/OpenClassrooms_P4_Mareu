package com.example.mareu.data.room;

import com.example.mareu.R;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Room data storage
 */
public class RoomBank {

    private static RoomBank mInstance;
    private final List<Room> mRooms;

    public RoomBank() {
        mRooms = Arrays.asList(
                new Room(1, "Salle 1", R.color.red),
                new Room(2, "Salle 2", R.color.orange),
                new Room(3, "Salle 3", R.color.yellow),
                new Room(4, "Salle 4", R.color.lime),
                new Room(5, "Salle 5", R.color.green),
                new Room(6, "Salle 6", R.color.light_blue),
                new Room(7, "Salle 7", R.color.blue),
                new Room(8, "Salle 8", R.color.brown),
                new Room(9, "Salle 9", R.color.purple),
                new Room(10, "Salle 10", R.color.pink)
        );
    }

    public static RoomBank getInstance() {
        if (mInstance == null) {
            mInstance = new RoomBank();
        }
        return mInstance;
    }

    /**
     * Get all {@link Room} entity
     *
     * @return Room list
     */
    public List<Room> getRooms() {
        return mRooms;
    }

    public Room getRoomById(long id) {
        for (Room room : mRooms) {
            if (room.getId() == id) {
                return room;
            }
        }

        return null;
    }

    /**
     * Get a random {@link Room}
     *
     * @return Room
     */
    public static Room getRandomRoom() {
        List<Room> list = RoomBank.getInstance().getRooms();
        return list.get(new Random().nextInt(list.size()));

    }

}
