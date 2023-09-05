package com.example.mareu.ui.meetingslist;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.data.meeting.Meeting;
import com.example.mareu.databinding.MeetingListItemBinding;

import java.util.stream.Collectors;

public class MeetingViewHolder extends RecyclerView.ViewHolder {

    private final MeetingListItemBinding binding;

    public MeetingViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = MeetingListItemBinding.bind(itemView);
    }

    public void bind(@NonNull Meeting meeting,@NonNull OnItemClickListener listener) {
        binding.meetingItemRoomColor.setColorFilter(ResourcesCompat.getColor(binding.getRoot().getResources(), meeting.getRoom().getColor(),null));
        binding.meetingItemTitle.setText(meeting.getFormattedSubject());
        binding.meetingItemParticipants.setText(
                meeting.getParticipantList()
                        .stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(","))
        );
        binding.meetingItemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteClick(meeting.getId());
            }
        });
    }
}
