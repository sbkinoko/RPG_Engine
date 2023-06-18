package com.sbkinoko.sbkinokorpg.battleframe.action_choice;

import com.sbkinoko.sbkinokorpg.battleframe.BattleConst;
import com.sbkinoko.sbkinokorpg.battleframe.BattleFrame;
import com.sbkinoko.sbkinokorpg.battleframe.BattleSystem;
import com.sbkinoko.sbkinokorpg.battleframe.status.Status;
import com.sbkinoko.sbkinokorpg.dataList.item.List_Skill;

public class SkillPlayerActionList extends PlayerActionList {

    @Override
    public int[] getActionItemList(Status nowPlayer) {
        int[] _nowList = new int[nowPlayer.getSkills().length];//null回避
        System.arraycopy(nowPlayer.getSkills(), 0, _nowList, 0, _nowList.length);
        return _nowList;
    }

    @Override
    public int getThisTurnAction() {
        return BattleConst.Action_Skill;
    }

    @Override
    public int getItemPosition(Status nowPlayer) {
        return nowPlayer.getLastSelectedSkill();
    }

    @Override
    public void setThisTurnChoice(Status nowPlayer, int thisTurnChoice) {
        nowPlayer.setLastSelectedSkill(thisTurnChoice);
    }

    public void backFromTarget(BattleFrame battleFrame, BattleSystem battleSystem) {
        battleFrame.battleWindow_chooseItem.openMenu(
                battleSystem.getPlayer(battleSystem.getWhoseActionSelect()),
                new List_Skill()
        );
    }
}
