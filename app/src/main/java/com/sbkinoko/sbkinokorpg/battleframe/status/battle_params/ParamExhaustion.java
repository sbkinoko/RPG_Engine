package com.sbkinoko.sbkinokorpg.battleframe.status.battle_params;

public abstract class ParamExhaustion extends BattleParam {
    int max;

    ParamExhaustion(int max, int value) {
        super(value);
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public void inc(int def) {
        value += def;
        if (max < this.value) {
            value = max;
        }
    }

    public void dec(int def) {
        value -= def;
        if (value < 0) {
            value = 0;
        }
    }

    public void setParam(int value) {
        this.value = value;
        if (max < this.value) {
            this.value = max;
        }
    }

    public String getRatio() {
        return value + "/" + max;
    }

}
