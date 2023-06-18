package com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata;

import com.sbkinoko.sbkinokorpg.mapframe.event.EventIDList;

public class EventBattle extends EventData {
    private final int battleID;

    public EventBattle(int keyStep, int afterStep, String[] txt,
                       int battleID) {
        super(keyStep, afterStep, txt);
        this.battleID = battleID;
    }

    public int getBattleID() {
        return battleID;
    }

    @Override
    public EventIDList getEventType() {
        return EventIDList.BATTLE_EVENT;
    }
}
