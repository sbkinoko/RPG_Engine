package com.sbkinoko.sbkinokorpg.battleframe.status.battle_params;

public class ATK extends ParamConst {
    public ATK(int value) {
        super(value);
    }

    @Override
    public BattleParams getParamID() {
        return BattleParams.ATK;
    }
}
