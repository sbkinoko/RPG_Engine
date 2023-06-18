package com.sbkinoko.sbkinokorpg.battleframe.status.battle_params;

public abstract class BattleParam {
    int value;

    BattleParam(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public abstract BattleParams getParamID();
}
