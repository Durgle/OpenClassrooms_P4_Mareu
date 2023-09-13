package com.example.mareu.ui.meetingslist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.mareu.R;
import com.example.mareu.data.meeting.Meeting;
import com.example.mareu.ui.meetingslist.listener.OnItemClickListener;

public class MeetingRecyclerViewAdapter extends ListAdapter<Meeting,MeetingViewHolder> {

    private final OnItemClickListener mListener;

    protected MeetingRecyclerViewAdapter(OnItemClickListener listener) {
        super(DIFF_CALLBACK);
        mListener = listener;
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
        holder.bind(getItem(position),mListener);
    }

    public static final DiffUtil.ItemCallback<Meeting> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Meeting>() {
                @Override
                public boolean areItemsTheSame(@NonNull Meeting oldMeeting, @NonNull Meeting newMeeting) {
                    return oldMeeting.getId() == newMeeting.getId();
                }
                @Override
                public boolean areContentsTheSame(@NonNull Meeting oldMeeting, @NonNull Meeting newMeeting) {
                    return oldMeeting.equals(newMeeting);
                }

            };


}
