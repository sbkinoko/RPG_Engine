package com.sbkinoko.sbkinokorpg.game_item.action_item.n_atk;

import com.sbkinoko.sbkinokorpg.battleframe.BattleConst;
import com.sbkinoko.sbkinokorpg.battleframe.status.Status;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ActionItem;
import com.sbkinoko.sbkinokorpg.mapframe.Player;

public class N_ATK extends ActionItem {

    public N_ATK(N_ATKData n_atkData) {
        super(n_atkData);
    }

    @Override
    public int getActionType() {
        return BattleConst.Action_NormalATK;
    }

    @Override
    public int getManipulatedValue(int status) {
        return status;
    }

    public void doAfterProcess(Status status,
                               Player mapPlayer,
                               boolean isInBattle,
                               int itemPosition) {
        //通常攻撃なので何もなし
    }

    @Override
    public String getActionTxt(Status nowPlayer) {
        return nowPlayer.getName() + "の\n通常攻撃";
    }

}
