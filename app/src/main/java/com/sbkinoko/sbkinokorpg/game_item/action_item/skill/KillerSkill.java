package com.sbkinoko.sbkinokorpg.game_item.action_item.skill;

import com.sbkinoko.sbkinokorpg.battleframe.condition.ConditionData;
import com.sbkinoko.sbkinokorpg.battleframe.condition.DefaultCondition;
import com.sbkinoko.sbkinokorpg.battleframe.status.Status;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.KillerItem;

public class KillerSkill extends Skill implements KillerItem {
    KillerSkill(KillerSkillData killerSkillData) {
        super(killerSkillData);
    }

    public ConditionData[] getKillerIDs() {
        return ((KillerSkillData) actionItemData).getKillerTargetIDs();
    }

    @Override
    public double killerMagnification(Status status) {
        DefaultCondition condition;
        condition = status.getCondition();
        do {
            for (int i = 0; i < ((KillerSkillData) actionItemData).getKillerTargetIDs().length; i++) {
                if (condition.getConditionData() == ((KillerSkillData) actionItemData).getKillerTargetIDs()[i]) {
                    return 1.5;
                }
            }
            condition = condition.getNextCondition();
        } while (condition != null);

        return 1.0;
    }
}
