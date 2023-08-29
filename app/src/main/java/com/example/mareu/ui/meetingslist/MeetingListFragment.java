package com.example.mareu.ui.meetingslist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mareu.data.meeting.Meeting;
import com.example.mareu.databinding.FragmentMeetingListBinding;
import com.example.mareu.injection.ViewModelFactory;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeetingListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeetingListFragment extends Fragment {

    private FragmentMeetingListBinding binding;
    private MeetingViewModel mViewModel;
    MeetingRecyclerViewAdapter mAdapter;

    public static MeetingListFragment newInstance() {
        return new MeetingListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MeetingRecyclerViewAdapter();
        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMeetingListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.initList();
        mViewModel.mMeetingList.observe(getViewLifecycleOwner(), list -> mAdapter.submitList(list));
        binding.meetingList.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}