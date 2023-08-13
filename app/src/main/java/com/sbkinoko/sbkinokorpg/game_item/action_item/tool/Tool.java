package com.sbkinoko.sbkinokorpg.game_item.action_item.tool;

import com.sbkinoko.sbkinokorpg.battleframe.BattleConst;
import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.battleframe.status.Status;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ActionItem;
import com.sbkinoko.sbkinokorpg.mapframe.player.Player;

public class Tool extends ActionItem {


    public Tool(ToolDataAction toolData) {
        super(toolData);
    }

    @Override
    public int getActionType() {
        return BattleConst.Action_Tool;
    }


    @Override
    public int getManipulatedValue(int status) {
        return getPower();
    }

    boolean isVanish() {
        return ((ToolDataAction) actionItemData).isVanish();
    }

    public void doAfterProcess(Status status,
                               Player mapPlayer,
                               boolean isInBattle,
                               int itemPosition) {
        if (!isVanish()) {
            LastItemUseUpDate.setIsLastItemUseUp(false);
            return;
        }

        //袋だったら
        if (status == null) {
            mapPlayer.decItem(itemPosition);
            return;
        }

        if (isInBattle) {
            status.setLastSelectedTool(0);//アイテムを使ったから先頭を選ぶ
        }

        ((PlayerStatus) status).decreaseItem(itemPosition);
    }

    @Override
    public String getActionTxt(Status nowPlayer) {
        return nowPlayer.getName() + "は\n" + getName() + "を使った";
    }

    public int getPrice() {
        return ((ToolDataAction) actionItemData).getPrice();
    }
}
