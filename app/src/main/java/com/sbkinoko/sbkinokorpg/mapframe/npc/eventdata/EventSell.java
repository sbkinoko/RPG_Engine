package com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata;

import com.sbkinoko.sbkinokorpg.mapframe.event.EventIDList;

public class EventSell extends EventData {
    public EventSell(int keyStep, int afterStep, String[] txt) {
        super(keyStep, afterStep, txt);
    }

    @Override
    public EventIDList getEventType() {
        return EventIDList.SELL_EVENT;
    }
}
