package com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata;

import com.sbkinoko.sbkinokorpg.mapframe.event.EventIDList;

public class EventItemGet extends EventData {
    private final int itemID;
    private final int itemNum;
    private final boolean nextTalk;

    public EventItemGet(int keyStep, int afterStep, String[] txt,
                        int itemID, int itemNum, boolean nextTalk) {
        super(keyStep, afterStep, txt);
        this.itemID = itemID;
        this.itemNum = itemNum;
        this.nextTalk = nextTalk;
    }

    public int getItemID() {
        return itemID;
    }

    public int getItemNum() {
        return itemNum;
    }

    public boolean isNextTalk() {
        return nextTalk;
    }

    @Override
    public EventIDList getEventType() {
        return EventIDList.ITEM_EVENT;
    }
}
