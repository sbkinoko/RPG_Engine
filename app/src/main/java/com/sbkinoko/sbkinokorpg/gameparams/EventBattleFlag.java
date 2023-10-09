package com.sbkinoko.sbkinokorpg.gameparams;

public enum EventBattleFlag {
    isEvent(true),
    isNotEvent(false),
    ;

    boolean eventFlag;

    EventBattleFlag(boolean eventFlag) {
        this.eventFlag = eventFlag;
    }

    public boolean isEventBattle() {
        return eventFlag;
    }

}
