package com.example.mareu.ui.meetingslist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.mareu.R;
import com.example.mareu.data.meeting.Meeting;

public class MeetingRecyclerViewAdapter extends ListAdapter<Meeting,MeetingViewHolder> {

    protected MeetingRecyclerViewAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meeting_list_item, parent, false);
        return new MeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static final DiffUtil.ItemCallback<Meeting> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Meeting>() {
                @Override
                public boolean areItemsTheSame(@NonNull Meeting oldUser, @NonNull Meeting newUser) {
                    return oldUser.getId() == newUser.getId();
                }
                @Override
                public boolean areContentsTheSame(@NonNull Meeting oldUser, @NonNull Meeting newUser) {
                    return oldUser.getFormattedSubject().equals(newUser.getFormattedSubject())
                    && oldUser.getParticipantList().equals(newUser.getParticipantList())
                    && oldUser.getRoom().getColor() == newUser.getRoom().getColor();
                }

            };
}
