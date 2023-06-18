package com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata;

import com.sbkinoko.sbkinokorpg.mapframe.event.EventIDList;

public class EventJob extends EventData {
    public EventJob(int keyStep, int afterStep) {
        super(keyStep, afterStep, null);
    }

    @Override
    public EventIDList getEventType() {
        return EventIDList.JOB_EVENT;
    }


}
