package com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata;

import com.sbkinoko.sbkinokorpg.mapframe.event.EventIDList;

public class EventTalk extends EventData {
    boolean nextTalk;

    public EventTalk(int keyStep, int afterStep, String[] txt,
                     boolean nextTalk) {
        super(keyStep, afterStep, txt);
        this.nextTalk = nextTalk;
    }

    public boolean isNextTalk() {
        return nextTalk;
    }

    @Override
    public EventIDList getEventType() {
        return EventIDList.TALK_EVENT;
    }
}
