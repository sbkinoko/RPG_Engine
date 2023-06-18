package com.sbkinoko.sbkinokorpg.battleframe.action_choice;

import com.sbkinoko.sbkinokorpg.battleframe.BattleConst;
import com.sbkinoko.sbkinokorpg.battleframe.BattleFrame;
import com.sbkinoko.sbkinokorpg.battleframe.BattleSystem;
import com.sbkinoko.sbkinokorpg.battleframe.status.Status;

public class NonPlayerActionList extends PlayerActionList {
    @Override
    public int[] getActionItemList(Status nowPlayer) {
        throw new RuntimeException();
    }

    @Override
    public int getItemPosition(Status nowPlayer) {
        throw new RuntimeException();
    }

    @Override
    public int getThisTurnAction() {
        return BattleConst.Action_NON;
    }

    @Override
    public void backFromTarget(BattleFrame battleFrame, BattleSystem battleSystem) {
        throw new RuntimeException();
    }
}
