package com.sbkinoko.sbkinokorpg.battleframe.window.window_action_type;

import com.sbkinoko.sbkinokorpg.battleframe.BattleSystem;
import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;

public abstract class Choice {
    abstract String getTxt();

    abstract void gotoNextWindow(PlayerStatus status, BattleSystem battleSystem);
}
