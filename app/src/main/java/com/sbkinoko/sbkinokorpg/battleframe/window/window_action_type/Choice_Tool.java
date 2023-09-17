package com.sbkinoko.sbkinokorpg.battleframe.window.window_action_type;

import com.sbkinoko.sbkinokorpg.battleframe.BattleSystem;
import com.sbkinoko.sbkinokorpg.battleframe.action_choice.ToolPlayerActionList;
import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.dataList.item.List_Tool;

public class Choice_Tool extends Choice {
    @Override
    String getTxt() {
        return "アイテム";
    }

    @Override
    void gotoNextWindow(PlayerStatus status, BattleSystem battleSystem) {
        status.setActionList(new ToolPlayerActionList());
        battleSystem.getBattleFrame().battleWindow_chooseItem.openMenu(status, new List_Tool());
    }
}
