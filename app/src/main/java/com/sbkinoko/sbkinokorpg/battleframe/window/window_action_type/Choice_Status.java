package com.sbkinoko.sbkinokorpg.battleframe.window.window_action_type;

import com.sbkinoko.sbkinokorpg.battleframe.BattleSystem;
import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;

public class Choice_Status extends Choice {
    @Override
    String getTxt() {
        return "ステータス";
    }


    @Override
    void gotoNextWindow(PlayerStatus status, BattleSystem battleSystem) {
        battleSystem.getBattleFrame().battleWindowStatus.openMenu(battleSystem.getWhoseActionSelect());
    }
}
