package com.example.mareu.data.room;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * Room entity
 */
public class Room {

    private final long mId;
    @NonNull
    private final String mName;

    private final int mColor;

    public Room(
            long id,
            @NonNull String name,
            int color
    ) {
        this.mId = id;
        this.mName = name;
        this.mColor = color;
    }

    public long getId() {
        return mId;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    public int getColor() {
        return mColor;
    }

    @NonNull
    @Override
    public String toString() {
        return mName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return mId == room.mId && mColor == room.mColor && mName.equals(room.mName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mName, mColor);
    }
}
