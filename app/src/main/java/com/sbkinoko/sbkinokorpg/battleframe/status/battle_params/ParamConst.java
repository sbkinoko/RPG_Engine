package com.sbkinoko.sbkinokorpg.battleframe.status.battle_params;

public abstract class ParamConst extends BattleParam {
    private int rank;

    public ParamConst(int value) {
        super(value);
    }

    public void resetRank() {
        rank = 0;
    }

    public int getRank() {
        return rank;
    }

    public void changeRank(int def) {
        rank += def;
    }

    public int getEffValue() {
        return value * (rank + 1);
    }


}
