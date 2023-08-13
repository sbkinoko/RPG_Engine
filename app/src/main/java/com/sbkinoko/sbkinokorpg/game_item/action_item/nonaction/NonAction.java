package com.sbkinoko.sbkinokorpg.game_item.action_item.nonaction;

import com.sbkinoko.sbkinokorpg.battleframe.BattleConst;
import com.sbkinoko.sbkinokorpg.battleframe.status.Status;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ActionItem;
import com.sbkinoko.sbkinokorpg.mapframe.player.Player;

public class NonAction extends ActionItem {
    public NonAction() {
        super(null);
    }

    @Override
    public int getActionType() {
        return BattleConst.Action_NON;
    }

    @Override
    public int getManipulatedValue(int status) {
        return 0;
    }

    @Override
    public String getActionTxt(Status nowPlayer) {
        return null;
    }

    @Override
    public void doAfterProcess(Status status,
                               Player mapPlayer,
                               boolean isInBattle,
                               int itemPosition) {
        //NonActionなのでなにもなし
    }
}
