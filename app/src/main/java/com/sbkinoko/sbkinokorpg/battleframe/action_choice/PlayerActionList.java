package com.sbkinoko.sbkinokorpg.battleframe.action_choice;

import com.sbkinoko.sbkinokorpg.battleframe.BattleFrame;
import com.sbkinoko.sbkinokorpg.battleframe.BattleSystem;
import com.sbkinoko.sbkinokorpg.battleframe.status.Status;

public abstract class PlayerActionList {

    abstract public int getThisTurnAction();

    public abstract int[] getActionItemList(Status nowPlayer);

    public abstract int getItemPosition(Status nowPlayer);

    public void setThisTurnChoice(Status nowPlayer, int thisTurnChoice) {

    }

    abstract public void backFromTarget(BattleFrame battleFrame, BattleSystem battleSystem);
}