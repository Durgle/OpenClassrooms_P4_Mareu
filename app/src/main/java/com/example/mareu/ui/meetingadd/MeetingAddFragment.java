package com.example.mareu.ui.meetingadd;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.mareu.R;
import com.example.mareu.data.room.Room;
import com.example.mareu.databinding.FragmentMeetingAddBinding;
import com.example.mareu.injection.ViewModelFactory;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeetingAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeetingAddFragment extends Fragment {

    private FragmentMeetingAddBinding binding;

    private AddMeetingViewModel mViewModel;
    private LocalTime selectedTime;
    private Room selectedRoom;


    public static MeetingAddFragment newInstance() {
        return new MeetingAddFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(AddMeetingViewModel.class);
        selectedTime = LocalTime.now();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMeetingAddBinding.inflate(inflater, container, false);
        requireActivity().setTitle(R.string.add_meeting_fragment_name);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel.isMeetingCreated().observe(
                getViewLifecycleOwner(),
                meetingCreated -> {
                    if (meetingCreated) {
                        Toast.makeText(requireContext(), "Meeting created successfully", Toast.LENGTH_SHORT).show();
                        getParentFragmentManager().popBackStack();
                    } else {
                        Snackbar.make(requireView(), "All field are required", Snackbar.LENGTH_LONG).show();
                    }
                }
        );

        initTimePicker();
        initRoomDropDown();
        initSubmitButton();
    }

    void initTimePicker() {
        binding.addMeetingFieldTime.setOnClickListener(v -> showTimePickerDialog());
    }

    void initSubmitButton() {
        binding.addMeetingSubmit.setOnClickListener(view -> {
            mViewModel.save(
                    new MeetingAddState(
                            selectedTime,
                            Objects.requireNonNull(binding.addMeetingFieldSubject.getText()).toString(),
                            selectedRoom,
                            Objects.requireNonNull(binding.addMeetingFieldParticipants.getText()).toString()
                    )
            );
        });
    }

    void initRoomDropDown() {
        ArrayAdapter<Room> adapter = new ArrayAdapter<>(
                getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                mViewModel.getRooms()
        );

        binding.addMeetingFieldRoom.setAdapter(adapter);
        binding.addMeetingFieldRoom.setOnItemClickListener(
                (parent, view1, position, id) -> selectedRoom = mViewModel.getRooms().get(position)
        );
    }

    private void showTimePickerDialog() {
        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(selectedTime.getHour())
                .setMinute(selectedTime.getMinute())
                .build();

        materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTime = LocalTime.of(materialTimePicker.getHour(), materialTimePicker.getMinute());
                updateTimePickerEditText();
            }
        });

        materialTimePicker.show(getParentFragmentManager(), "time_picker");
    }

    private void updateTimePickerEditText() {
        String formattedTime = selectedTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        binding.addMeetingFieldTime.setText(formattedTime);
    }


}