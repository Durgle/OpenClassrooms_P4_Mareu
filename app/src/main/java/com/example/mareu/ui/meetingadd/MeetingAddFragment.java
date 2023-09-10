package com.example.mareu.ui.meetingadd;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeetingAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeetingAddFragment extends Fragment {

    private FragmentMeetingAddBinding binding;
    private AddMeetingViewModel mViewModel;

    public static MeetingAddFragment newInstance() {
        return new MeetingAddFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(AddMeetingViewModel.class);
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

        mViewModel.getErrorMessage().observe(
                getViewLifecycleOwner(),
                errorMessage -> Snackbar.make(requireView(), errorMessage, Snackbar.LENGTH_SHORT).show()
        );
        mViewModel.isMeetingCreated().observe(
                getViewLifecycleOwner(),
                meetingCreated -> {
                    if(meetingCreated) {
                        Toast.makeText(requireContext(), R.string.add_meeting_success, Toast.LENGTH_SHORT).show();
                        getParentFragmentManager().popBackStack();
                    }
                });

        initSubjectInput();
        initTimePicker();
        initRoomDropDown();
        initParticipantInput();
        initSubmitButton();
    }

    void initSubjectInput(){
        binding.addMeetingFieldSubject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mViewModel.onSubjectChanged(editable.toString());
            }
        });
    }

    void initParticipantInput() {
        binding.addMeetingFieldParticipants.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mViewModel.onParticipantChanged(editable.toString());
            }
        });
    }

    void initTimePicker() {
        mViewModel.getSelectedTime().observe(
                getViewLifecycleOwner(), localTime -> {
            binding.addMeetingFieldTime.setText(localTime);
        });
        binding.addMeetingFieldTime.setOnClickListener(v -> showTimePickerDialog());
    }

    private void showTimePickerDialog() {
        LocalTime localTime = LocalTime.now();
        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(localTime.getHour())
                .setMinute(localTime.getMinute())
                .build();

        materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.onSelectedTime(materialTimePicker.getHour(),materialTimePicker.getMinute());
            }
        });

        materialTimePicker.show(getParentFragmentManager(), "time_picker");
    }

    void initSubmitButton() {
        binding.addMeetingSubmit.setOnClickListener(view -> {
            mViewModel.save();
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
                (parent, view1, position, id) -> mViewModel.onSelectedRoom(position)
        );
    }

}