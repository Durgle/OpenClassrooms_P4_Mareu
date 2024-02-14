package com.example.mareu.data.meeting;

import com.example.mareu.data.room.RoomBank;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Meeting data storage
 */
public class MeetingBank {

    private static MeetingBank mInstance;
    private final List<Meeting> mMeetings;

    public MeetingBank() {
        mMeetings = createFakeMeeting();
    }

    /**
     * Get a fake {@link Meeting} list
     *
     * @return Meeting list
     */
    private List<Meeting> createFakeMeeting() {
        List<Meeting> fakeMeetings = new ArrayList<>();
        int meetingId = 1;
        for (int i = 0; i < 5; i++) {
            Random r = new Random();
            int hour = r.nextInt(11) + 1;
            int min = r.nextInt(59);

            Meeting meeting = new Meeting(
                    meetingId, LocalTime.of(hour, min),
                    RoomBank.getRandomRoom(),
                    String.format("%s %s", "Meeting", meetingId),
                    Arrays.asList("user1@email.com", "user2@email.com", "user3@email.com")
            );
            fakeMeetings.add(meeting);
            meetingId++;
        }

        return fakeMeetings;
    }

    /**
     * Get list of {@link Meeting} for test
     *
     * @return Meeting list
     */
    public List<Meeting> getMeetingForTest() {
        return Arrays.asList(
                new Meeting(
                        1, LocalTime.of(0, 0),
                        RoomBank.getInstance().getRoomById(1),
                        "Meeting 1",
                        Arrays.asList("user1@email.com", "user2@email.com", "user3@email.com")
                ),
                new Meeting(
                        2, LocalTime.of(1, 10),
                        RoomBank.getInstance().getRoomById(2),
                        "Meeting 2",
                        Arrays.asList("user1@email.com", "user2@email.com", "user3@email.com")
                ),
                new Meeting(
                        3, LocalTime.of(2, 20),
                        RoomBank.getInstance().getRoomById(3),
                        "Meeting 3",
                        Arrays.asList("user1@email.com", "user2@email.com", "user3@email.com")
                ),
                new Meeting(
                        4, LocalTime.of(3, 30),
                        RoomBank.getInstance().getRoomById(4),
                        "Meeting 4",
                        Arrays.asList("user1@email.com", "user2@email.com", "user3@email.com")
                ),
                new Meeting(
                        5, LocalTime.of(4, 40),
                        RoomBank.getInstance().getRoomById(5),
                        "Meeting 5",
                        Arrays.asList("user1@email.com", "user2@email.com", "user3@email.com")
                ),
                new Meeting(
                        6, LocalTime.of(5, 50),
                        RoomBank.getInstance().getRoomById(6),
                        "Meeting 6",
                        Arrays.asList("user1@email.com", "user2@email.com", "user3@email.com")
                ),
                new Meeting(
                        7, LocalTime.of(6, 0),
                        RoomBank.getInstance().getRoomById(7),
                        "Meeting 7",
                        Arrays.asList("user1@email.com", "user2@email.com", "user3@email.com")
                ),
                new Meeting(
                        8, LocalTime.of(7, 10),
                        RoomBank.getInstance().getRoomById(8),
                        "Meeting 8",
                        Arrays.asList("user1@email.com", "user2@email.com", "user3@email.com")
                ),
                new Meeting(
                        9, LocalTime.of(8, 20),
                        RoomBank.getInstance().getRoomById(9),
                        "Meeting 9",
                        Arrays.asList("user1@email.com", "user2@email.com", "user3@email.com")
                ),
                new Meeting(
                        10, LocalTime.of(9, 30),
                        RoomBank.getInstance().getRoomById(10),
                        "Meeting 10",
                        Arrays.asList("user1@email.com", "user2@email.com", "user3@email.com")
                )
        );
    }

    public static MeetingBank getInstance() {
        if (mInstance == null) {
            mInstance = new MeetingBank();
        }
        return mInstance;
    }

    public List<Meeting> getMeetings() {
        return mMeetings;

    }

}
