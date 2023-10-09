package com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata;

import com.sbkinoko.sbkinokorpg.mapframe.event.EventIDList;

public class EventBattle extends EventData {
    private final int battleID;

    public EventBattle(int keyStep,
                       int battleID) {
        super(keyStep, keyStep, null);
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
