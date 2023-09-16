package com.sbkinoko.sbkinokorpg.game_item.action_item.skill;

import com.sbkinoko.sbkinokorpg.battleframe.condition.ConditionData;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ConditionItemData;
import com.sbkinoko.sbkinokorpg.gameparams.WhereCanUse;
import com.sbkinoko.sbkinokorpg.mylibrary.ArrayToProb;

public class ConditionSkillData extends SkillData implements ConditionItemData {
    ConditionData conditionData;
    ArrayToProb arrayToProb;

    public ConditionSkillData(String name, WhereCanUse where, int effect, ConditionData condition,
                              ArrayToProb arrayToProb,
                              int targetNum, int MP) {
        super(name, where, effect, 0, 0, targetNum, MP);
        this.conditionData = condition;
        this.arrayToProb = arrayToProb;
    }

    @Override
    public ConditionData getConditionId() {
        return conditionData;
    }

    @Override
    public Skill getSkill() {
        return new ConditionSkill(this);
    }

    @Override
    public int getRestTurn() {
        return arrayToProb.getDatum();
    }
}
