package com.sbkinoko.sbkinokorpg.battleframe.window.window_action_type;

import com.sbkinoko.sbkinokorpg.battleframe.BattleSystem;
import com.sbkinoko.sbkinokorpg.battleframe.action_choice.SkillPlayerActionList;
import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.dataList.item.List_Skill;

public class Choice_Skill extends Choice {
    @Override
    String getTxt() {
        return "特技";
    }


    @Override
    void gotoNextWindow(PlayerStatus status, BattleSystem battleSystem) {
        status.setActionList(new SkillPlayerActionList());
        battleSystem.getBattleFrame().battleWindow_chooseItem.openMenu(status, new List_Skill());
    }
}
