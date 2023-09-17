package com.sbkinoko.sbkinokorpg.battleframe.action_choice;

import com.sbkinoko.sbkinokorpg.battleframe.BattleConst;
import com.sbkinoko.sbkinokorpg.battleframe.BattleFrame;
import com.sbkinoko.sbkinokorpg.battleframe.BattleSystem;
import com.sbkinoko.sbkinokorpg.battleframe.status.Status;

public class NormalAtkPlayerActionList extends PlayerActionList {
    @Override
    public int getThisTurnAction() {
        return BattleConst.Action_NormalATK;
    }

    @Override
    public int getItemPosition(Status nowPlayer) {
        return 1;
    }

    @Override
    public void backFromTarget(BattleFrame battleFrame, BattleSystem battleSystem) {
        battleFrame.battleWindow_chooseAction.openMenu();
    }

    @Override
    public int[] getActionItemList(Status nowPlayer) {
        return new int[]{1};
    }
}
