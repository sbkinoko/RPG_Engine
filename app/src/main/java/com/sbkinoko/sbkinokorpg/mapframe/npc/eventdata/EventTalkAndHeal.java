package com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata;

import com.sbkinoko.sbkinokorpg.mapframe.event.EventIDList;

public class EventTalkAndHeal extends EventTalk {
    public EventTalkAndHeal(int keyStep, int afterStep, String[] txt,
                            boolean nextTalk) {
        super(keyStep, afterStep, txt, nextTalk);
    }

    @Override
    public EventIDList getEventType() {
        return EventIDList.TALK_AND_HEAL;
    }
}
