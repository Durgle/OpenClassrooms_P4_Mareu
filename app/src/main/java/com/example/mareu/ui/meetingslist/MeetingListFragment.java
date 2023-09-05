package com.example.mareu.ui.meetingslist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mareu.R;
import com.example.mareu.databinding.FragmentMeetingListBinding;
import com.example.mareu.injection.ViewModelFactory;
import com.example.mareu.ui.meetingadd.MeetingAddFragment;

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
        mAdapter = new MeetingRecyclerViewAdapter(meetingId -> mViewModel.deleteMeeting(meetingId));
        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMeetingListBinding.inflate(inflater, container, false);
        requireActivity().setTitle(R.string.app_name);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.initList();
        mViewModel.getMeetingList().observe(getViewLifecycleOwner(), list -> mAdapter.submitList(list));
        binding.meetingList.setAdapter(mAdapter);
        binding.addMeeting.setOnClickListener(view1 -> getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, MeetingAddFragment.newInstance())
                .addToBackStack("startNewMeeting")
                .commit()
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}