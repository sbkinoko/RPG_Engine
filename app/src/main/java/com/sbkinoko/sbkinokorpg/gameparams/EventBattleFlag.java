package com.sbkinoko.sbkinokorpg.gameparams;

public enum EventBattleFlag {
    Event(true),
    NotEvent(false),
    ;
    //fixme　booleanを持たせるのをやめて比較にする
    //例)　eventFlag == EventFlag.Event とか
    boolean eventFlag;

    EventBattleFlag(boolean eventFlag) {
        this.eventFlag = eventFlag;
    }

    public boolean isEventBattle() {
        return eventFlag;
    }

}
