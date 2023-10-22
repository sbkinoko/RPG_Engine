package com.sbkinoko.sbkinokorpg.gameparams;

public enum EventBattleFlag {
    Event(true),
    NotEvent(false),
    ;

    boolean eventFlag;

    EventBattleFlag(boolean eventFlag) {
        this.eventFlag = eventFlag;
    }

    public boolean isEventBattle() {
        return eventFlag;
    }

}
