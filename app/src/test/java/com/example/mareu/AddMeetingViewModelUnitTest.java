package com.example.mareu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.mareu.data.meeting.Meeting;
import com.example.mareu.data.meeting.MeetingRepository;
import com.example.mareu.data.room.Room;
import com.example.mareu.data.room.RoomBank;
import com.example.mareu.data.room.RoomRepository;
import com.example.mareu.ui.meetingadd.AddMeetingViewModel;
import com.example.mareu.ui.utils.MessageTypeEnum;
import com.example.mareu.utils.LiveDataTestUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;


@RunWith(JUnit4.class)
public class AddMeetingViewModelUnitTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
    }

    @Test
    public void getRoomsWithSuccess() {
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        Mockito.when(roomRepository.getRooms()).thenReturn(RoomBank.getInstance().getRooms());
        AddMeetingViewModel addMeetingViewModel = new AddMeetingViewModel(new MeetingRepository(), roomRepository);
        List<Room> rooms = addMeetingViewModel.getRooms();
        assertEquals(rooms, roomRepository.getRooms());
    }

    @Test
    public void saveWithSuccess() throws InterruptedException {
        // TODO : voir comment faire pour test chaque OnMethod
        
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        Mockito.when(roomRepository.getRooms()).thenReturn(RoomBank.getInstance().getRooms());
        MeetingRepository meetingRepository = new MeetingRepository();
        AddMeetingViewModel addMeetingViewModel = new AddMeetingViewModel(meetingRepository, roomRepository);
        int min = 0;
        int hours = 12;
        String subject = "Test save";
        String participants = "email1@test.fr;email2@test.fr";
        int roomPosition = 2;
        Room room = roomRepository.getRooms().get(roomPosition);
        addMeetingViewModel.onSelectedTime(hours, min);
        addMeetingViewModel.onSubjectChanged(subject);
        addMeetingViewModel.onSelectedRoom(roomPosition);
        addMeetingViewModel.onParticipantChanged(participants);
        addMeetingViewModel.save();

        MessageTypeEnum messageTypeEnum = LiveDataTestUtil.getOrAwaitValue(addMeetingViewModel.getMessage());
        assertTrue(messageTypeEnum.isSuccess());
        List<Meeting> meetingList = meetingRepository.getMeetingList();
        assertEquals(1, meetingList.size());
        Meeting meeting = meetingList.get(0);
        assertEquals(meeting.getTime(), LocalTime.of(hours, min));
        assertEquals(meeting.getRoom(), room);
        assertEquals(meeting.getSubject(), subject);
        assertEquals(meeting.getParticipantList(), Arrays.asList(participants.split(",")));
        assertEquals(meeting.getParticipantList(), Arrays.asList(participants.split(",")));
    }

    @Test
    public void noSaveWhenValueIsEmpty() throws InterruptedException {
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        Mockito.when(roomRepository.getRooms()).thenReturn(RoomBank.getInstance().getRooms());
        MeetingRepository meetingRepository = new MeetingRepository();
        AddMeetingViewModel addMeetingViewModel = new AddMeetingViewModel(meetingRepository, roomRepository);

        addMeetingViewModel.save();
        MessageTypeEnum messageTypeEnum = LiveDataTestUtil.getOrAwaitValue(addMeetingViewModel.getMessage());
        assertFalse(messageTypeEnum.isSuccess());
        assertTrue(meetingRepository.getMeetingList().isEmpty());
    }

}
