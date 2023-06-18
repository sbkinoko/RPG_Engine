package com.sbkinoko.sbkinokorpg.battleframe.status.battle_params;

public class MINT extends ParamConst {
    public MINT(int value) {
        super(value);
    }

    @Override
    public BattleParams getParamID() {
        return BattleParams.INT;
    }
}
