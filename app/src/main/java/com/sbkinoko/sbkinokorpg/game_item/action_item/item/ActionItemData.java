package com.sbkinoko.sbkinokorpg.game_item.action_item.item;

import com.sbkinoko.sbkinokorpg.gameparams.WhereCanUse;

public class ActionItemData {

    private final int
            effect,
            targetNum,
            attribute,
            power;

    private final WhereCanUse
            whereCanUse;

    private final String name;

    public ActionItemData(String name, WhereCanUse where, int effect, int attribute, int power,
                          int targetNum) {
        this.name = name;
        this.whereCanUse = where;
        this.effect = effect;
        this.attribute = attribute;
        this.power = power;
        this.targetNum = targetNum;
    }

    public WhereCanUse getWhereCanUse() {
        return whereCanUse;
    }

    public int getEffect() {
        return effect;
    }

    public String getName() {
        return name;
    }

    public int getTargetNum() {
        return targetNum;
    }

    public int getAtr() {
        return attribute;
    }

    public int getPower() {
        return power;
    }
}
