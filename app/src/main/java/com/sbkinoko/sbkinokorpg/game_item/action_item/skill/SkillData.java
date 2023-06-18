package com.sbkinoko.sbkinokorpg.game_item.action_item.skill;

import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ActionItemData;

public class SkillData extends ActionItemData {
    private final int MP;

    public SkillData(String name, int where, int effect, int attribute, int power,
                     int targetNum, int MP) {
        super(name, where, effect, attribute, power, targetNum);
        this.MP = MP;
    }


    public int getNeedMP() {
        return MP;
    }

    public Skill getSkill() {
        return new Skill(this);
    }
}
