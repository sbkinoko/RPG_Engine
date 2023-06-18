package com.sbkinoko.sbkinokorpg.battleframe.status.battle_params;

public class HP extends ParamExhaustion {
    public HP(int max, int value) {
        super(max, value);
    }

    @Override
    public BattleParams getParamID() {
        return BattleParams.HP;
    }
}
