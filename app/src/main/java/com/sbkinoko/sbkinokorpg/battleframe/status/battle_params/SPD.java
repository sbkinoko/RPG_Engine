package com.sbkinoko.sbkinokorpg.battleframe.status.battle_params;

public class SPD extends ParamConst {
    public SPD(int value) {
        super(value);
    }

    @Override
    public BattleParams getParamID() {
        return BattleParams.SPD;
    }
}
