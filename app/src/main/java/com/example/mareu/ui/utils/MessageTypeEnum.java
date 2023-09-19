package com.example.mareu.ui.utils;

import com.example.mareu.R;

/**
 * Enum with all error and success message
 */
public enum MessageTypeEnum {

    SUCCESS(R.string.add_meeting_success, true),
    ERROR_ROOM(R.string.add_meeting_room_error, false),
    ERROR_SUBJECT(R.string.add_meeting_subject_error, false),
    ERROR_TIME(R.string.add_meeting_time_error, false),
    ERROR_PARTICIPANT(R.string.add_meeting_participant_error, false);

    private final int mMessageResource;
    private final boolean mIsSuccess;

    /**
     * @param messageResource Id of resource message
     * @param isSuccess       Define if the message is a success
     */
    MessageTypeEnum(int messageResource, boolean isSuccess) {
        mMessageResource = messageResource;
        mIsSuccess = isSuccess;
    }

    /**
     * @return Id of resource message
     */
    public int getMessageResource() {
        return mMessageResource;
    }

    /**
     * @return Return true if he is a success message
     */
    public boolean isSuccess() {
        return mIsSuccess;
    }
}
