package com.itc.suppaperless.meetingmodule.eventbean;

/**
 * Created by xiaogf on 19-2-28.
 */

public class DeleteOrModifyEvent {
    private int fileId;

    public DeleteOrModifyEvent(int fileId) {
        this.fileId = fileId;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
}
