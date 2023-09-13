package com.example.mareu.ui.meetingfilter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mareu.R;
import com.example.mareu.data.room.Room;
import com.example.mareu.databinding.DialogFilterBinding;
import com.example.mareu.injection.ViewModelFactory;
import com.example.mareu.ui.meetingslist.listener.OnFilterAppliedListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.LocalTime;

public class FilterDialogFragment extends DialogFragment {

    private DialogFilterBinding binding;
    private FilterViewModel mViewModel;
    private OnFilterAppliedListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(FilterViewModel.class);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_filter, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(view);
        builder.setPositiveButton(R.string.filter, (dialog, which) -> {
            listener.onFilterApplied(mViewModel.getSelectedTime().getValue(),mViewModel.getSelectedRoom().getValue());
            dismiss();
        }).setNegativeButton(R.string.cancel, (dialog, which) -> {
            dismiss();
        }).setNeutralButton(R.string.clear_filter, (dialog, which) -> {
            mViewModel.clearFilters();
        });
        binding = DialogFilterBinding.bind(view);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (OnFilterAppliedListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initRoomDropDown();
        initTimeSelector();
        mViewModel.getFormattedSelectedTime().observe(
                getViewLifecycleOwner(), localTime -> {
                    if(localTime != null) {
                        binding.dialogFieldTime.setText(localTime);
                    }
                });
        return binding.getRoot();
    }

    public void setListener(OnFilterAppliedListener listener) {
        this.listener = listener;
    }

    void initRoomDropDown() {
        ArrayAdapter<Room> adapter = new ArrayAdapter<>(
                getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                mViewModel.getRooms()
        );

        binding.dialogFieldRoom.setAdapter(adapter);
        binding.dialogFieldRoom.setOnItemClickListener(
                (parent, view1, position, id) -> mViewModel.onSelectedRoom(position)
        );
    }

    void initTimeSelector() {
        binding.dialogFieldTime.setOnClickListener(v -> showTimePickerDialog());
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

}
