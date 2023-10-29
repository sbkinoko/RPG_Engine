package com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata;

import com.sbkinoko.sbkinokorpg.gameparams.EscapeFlag;
import com.sbkinoko.sbkinokorpg.mapframe.event.EventIDList;

public class EventBattle extends EventData {
    private final int battleID;
    private final EscapeFlag escapeFlag;

    public EventBattle(int keyStep,
                       int battleID,
                       EscapeFlag escapeFlag) {
        super(keyStep, keyStep, null);
        this.battleID = battleID;
        this.escapeFlag = escapeFlag;
    }

    public int getBattleID() {
        return battleID;
    }

    @Override
    public EventIDList getEventType() {
        switch (escapeFlag) {
            case Can:
                return EventIDList.BATTLE_EVENT_CAN_ESCAPE;
            case CanNot:
                return EventIDList.BATTLE_EVENT_NOT_ESCAPE;
        }
        throw new RuntimeException("EscapeFlagが不正です");
    }
}
