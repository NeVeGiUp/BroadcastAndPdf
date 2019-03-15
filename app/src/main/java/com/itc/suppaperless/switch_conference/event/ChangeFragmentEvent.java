package com.itc.suppaperless.switch_conference.event;

/**
 * Created by cong on 19-1-23.
 */

public class ChangeFragmentEvent {
    private int position;

    public ChangeFragmentEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

}
