package com.sbkinoko.sbkinokorpg.game_item.action_item.item;

public class ActionItemData {

    private final int
            whereCanUse,
            effect,
            targetNum,
            attribute,
            power;

    private final String name;

    public ActionItemData(String name, int where, int effect, int attribute, int power,
                          int targetNum) {
        this.name = name;
        this.whereCanUse = where;
        this.effect = effect;
        this.attribute = attribute;
        this.power = power;
        this.targetNum = targetNum;
    }

    public int getWhereCanUse() {
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
