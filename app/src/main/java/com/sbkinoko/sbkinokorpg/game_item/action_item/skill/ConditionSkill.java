package com.sbkinoko.sbkinokorpg.game_item.action_item.skill;

import com.sbkinoko.sbkinokorpg.battleframe.condition.ConditionData;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ConditionItem;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ConditionItemData;

public class ConditionSkill extends Skill implements ConditionItem {
    ConditionSkill(ConditionSkillData conditionSkillData) {
        super(conditionSkillData);
    }

    @Override
    public ConditionData getConditionId() {
        return ((ConditionItemData) actionItemData).getConditionId();
    }

    @Override
    public int getRestTurn() {
        return ((ConditionSkillData) actionItemData).getRestTurn();
    }
}
