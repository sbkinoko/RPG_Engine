package com.sbkinoko.sbkinokorpg.game_item.action_item.skill;

import com.sbkinoko.sbkinokorpg.game_item.action_item.item.WarpItem;

public class WarpSkill extends Skill implements WarpItem {

    public WarpSkill(WarpSkillData skillData) {
        super(skillData);
    }

    @Override
    public int[] getTownId() {
        return ((WarpSkillData) actionItemData).getTownId();
    }

    @Override
    public int getWindowId() {
        return ((WarpSkillData) actionItemData).getWindowId();
    }
}
