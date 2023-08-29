package com.example.mareu.ui.meetingslist;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.data.meeting.Meeting;
import com.example.mareu.databinding.MeetingListItemBinding;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class MeetingViewHolder extends RecyclerView.ViewHolder {

    private MeetingListItemBinding binding;

    public MeetingViewHolder(@NonNull View itemView) {
        super(itemView);
        this.binding = MeetingListItemBinding.bind(itemView);
    }

    public void bind(Meeting meeting) {
        binding.meetingItemRoomColor.setColorFilter(ResourcesCompat.getColor(binding.getRoot().getResources(), meeting.getRoom().getColor(),null));
        binding.meetingItemTitle.setText(
                String.format("%s - %s - %s",
                        meeting.getSubject(),
                        meeting.getTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                        meeting.getRoom().getName()
                )
        );
        binding.meetingItemParticipants.setText(
                meeting.getParticipantList()
                        .stream()
                        .map(n -> String.valueOf(n))
                        .collect(Collectors.joining(","))
        );
    }
}
