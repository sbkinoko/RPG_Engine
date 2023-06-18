package com.sbkinoko.sbkinokorpg.game_item.action_item.skill;

import com.sbkinoko.sbkinokorpg.game_item.action_item.item.SuccessiveItem;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.SuccessiveItemData;

public class SuccessiveSkill extends Skill implements SuccessiveItem {
    int atkCount = 0;

    public SuccessiveSkill(SuccessiveSkillData successiveSkillData) {
        super(successiveSkillData);
    }

    @Override
    public boolean isStoppable() {
        return ((SuccessiveItemData) actionItemData).isStoppable();
    }

    @Override
    public int getSuccessiveNum() {
        return ((SuccessiveItemData) actionItemData).getSuccessiveNum();
    }

    @Override
    public int getATKCount() {
        return atkCount;
    }

    @Override
    public void incATKCount() {
        atkCount++;
    }
}
