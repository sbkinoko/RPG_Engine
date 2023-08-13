package com.sbkinoko.sbkinokorpg.game_item.action_item.item;

import com.sbkinoko.sbkinokorpg.battleframe.status.Status;
import com.sbkinoko.sbkinokorpg.game_item.GameItem;
import com.sbkinoko.sbkinokorpg.mapframe.player.Player;

public abstract class ActionItem implements GameItem {
    protected ActionItemData actionItemData;

    public ActionItem(ActionItemData actionItemData) {
        this.actionItemData = actionItemData;
    }

    public abstract int getActionType();

    public int getWhereCanUse() {
        return actionItemData.getWhereCanUse();
    }

    public int getEffect() {
        return actionItemData.getEffect();
    }

    public String getName() {
        return actionItemData.getName();
    }

    public int getTargetNum() {
        return actionItemData.getTargetNum();
    }

    public int getAtr() {
        return actionItemData.getAtr();
    }

    public int getPower() {
        return actionItemData.getPower();
    }

    public abstract int getManipulatedValue(int status);

    public abstract void doAfterProcess(Status status,
                                        Player mapPlayer,
                                        boolean isInBattle,
                                        int itemPosition);

    public abstract String getActionTxt(Status nowPlayer);

    public boolean isLackOfMP(int MP) {
        return false;
    }

    public double killerMagnification(Status status) {
        return 1.0;
    }

}
