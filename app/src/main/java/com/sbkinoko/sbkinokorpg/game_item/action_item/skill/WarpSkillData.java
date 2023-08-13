package com.sbkinoko.sbkinokorpg.game_item.action_item.skill;

import com.sbkinoko.sbkinokorpg.game_item.action_item.item.WarpItemData;
import com.sbkinoko.sbkinokorpg.gameparams.GameParams;
import com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList;

public class WarpSkillData extends SkillData implements WarpItemData {
    int[] townId;

    public WarpSkillData(String name, int where, int MP, int[] townId) {
        super(name, where, GameParams.EFFECT_TYPE_WARP, 0, 0, 0, MP);
        this.townId = townId;
    }

    @Override
    public int[] getTownId() {
        return townId;
    }

    @Override
    public Skill getSkill() {
        return new WarpSkill(this);
    }

    @Override
    public int getWindowId() {
        return WindowIdList.NUM_MapMenu_WARP;
    }
}
