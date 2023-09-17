package com.sbkinoko.sbkinokorpg.battleframe.window.window_action_type;

import com.sbkinoko.sbkinokorpg.battleframe.BattleSystem;
import com.sbkinoko.sbkinokorpg.battleframe.action_choice.NormalAtkPlayerActionList;
import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.dataList.item.List_NormalATK;
import com.sbkinoko.sbkinokorpg.game_item.action_item.n_atk.N_ATK;

public class Choice_NormalAtk extends Choice {
    @Override
    String getTxt() {
        return "攻撃";
    }

    @Override
    void gotoNextWindow(PlayerStatus status, BattleSystem battleSystem) {
        status.setActionItem(new N_ATK(List_NormalATK.getN_ATKDataAt(1)));
        status.setActionList(new NormalAtkPlayerActionList());
        battleSystem.getBattleFrame().battleChooseEnmWindow.openMenu(status);
    }
}
