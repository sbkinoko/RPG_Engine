package com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata;

import com.sbkinoko.sbkinokorpg.mapframe.event.EventIDList;

public abstract class EventData {
    private final int keyStep;
    private final int afterStep;
    private final String[] txt;

    EventData(int keyStep, int afterStep, String[] txt) {
        this.keyStep = keyStep;
        this.afterStep = afterStep;
        this.txt = txt;
    }

    public int getKeyStep() {
        return keyStep;
    }

    public int getAfterStep() {
        return afterStep;
    }

    public String[] getTxt() {
        return txt;
    }

    public abstract EventIDList getEventType();
}
