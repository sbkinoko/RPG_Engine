package com.sbkinoko.sbkinokorpg.battleframe.status.battle_params;

public class MP extends ParamExhaustion {
    public MP(int max, int value) {
        super(max, value);
    }

    @Override
    public BattleParams getParamID() {
        return BattleParams.MP;
    }
}
