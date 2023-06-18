package com.sbkinoko.sbkinokorpg.game_item.action_item.skill;

import com.sbkinoko.sbkinokorpg.battleframe.condition.ConditionData;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.KillerItemData;

public class KillerSkillData extends SkillData implements KillerItemData {
    ConditionData[] killerTargetIDs;

    public KillerSkillData(String name, int where, int effect, int attribute, int power,
                           int targetNum, int MP, ConditionData[] killerTargetIDs) {
        super(name, where, effect, attribute, power, targetNum, MP);
        this.killerTargetIDs = killerTargetIDs;
    }

    @Override
    public ConditionData[] getKillerTargetIDs() {
        return killerTargetIDs;
    }

    @Override
    public Skill getSkill() {
        return new KillerSkill(this);
    }
}
