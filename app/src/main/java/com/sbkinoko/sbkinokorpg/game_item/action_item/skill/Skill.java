package com.sbkinoko.sbkinokorpg.game_item.action_item.skill;

import com.sbkinoko.sbkinokorpg.battleframe.BattleConst;
import com.sbkinoko.sbkinokorpg.battleframe.status.Status;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ActionItem;
import com.sbkinoko.sbkinokorpg.mapframe.player.Player;

public class Skill extends ActionItem {

    public Skill(SkillData skillData) {
        super(skillData);
    }

    @Override
    public int getActionType() {
        return BattleConst.Action_Skill;
    }

    public int getNeedMP() {
        return ((SkillData) actionItemData).getNeedMP();
    }

    @Override
    public int getManipulatedValue(int status) {
        return status * getPower() / 10;
    }

    public void doAfterProcess(Status status,
                               Player mapPlayer,
                               boolean isInBattle,
                               int itemPosition) {
        status.decMP(getNeedMP());
    }

    @Override
    public String getActionTxt(Status nowPlayer) {
        return nowPlayer.getName() + "の\n" + getName();
    }

    @Override
    public boolean isLackOfMP(int MP) {
        return MP < getNeedMP();
    }
}

