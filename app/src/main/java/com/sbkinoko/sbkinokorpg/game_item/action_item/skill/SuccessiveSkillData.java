package com.sbkinoko.sbkinokorpg.game_item.action_item.skill;

import com.sbkinoko.sbkinokorpg.game_item.action_item.item.SuccessiveItemData;
import com.sbkinoko.sbkinokorpg.gameparams.WhereCanUse;
import com.sbkinoko.sbkinokorpg.mylibrary.ArrayToProb;

public class SuccessiveSkillData extends SkillData implements SuccessiveItemData {
    ArrayToProb arrayToProb;
    boolean isStoppable;

    public SuccessiveSkillData(String name, WhereCanUse where, int effect,
                               int attribute, int power,
                               int targetNum, int MP,
                               boolean stoppable, ArrayToProb arrayToProb) {
        super(name, where, effect, attribute, power, targetNum, MP);
        this.arrayToProb = arrayToProb;
        this.isStoppable = stoppable;
    }

    @Override
    public int getSuccessiveNum() {
        return arrayToProb.getDatum();
    }

    @Override
    public boolean isStoppable() {
        return isStoppable;
    }

    @Override
    public Skill getSkill() {
        return new SuccessiveSkill(this);
    }
}
