package com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata;


import com.sbkinoko.sbkinokorpg.mapframe.event.EventIDList;

public class EventShopping extends EventData {
    private final int[] items;

    public EventShopping(int keyStep, int afterStep, String[] txt,
                         int[] items) {
        super(keyStep, afterStep, txt);
        this.items = items;
    }

    public int[] getItems() {
        return items;
    }

    @Override
    public EventIDList getEventType() {
        return EventIDList.SHOPPING_EVENT;
    }
}
