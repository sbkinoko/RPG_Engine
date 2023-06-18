package com.sbkinoko.sbkinokorpg.game_item.action_item.tool;

import com.sbkinoko.sbkinokorpg.battleframe.BattleConst;
import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.battleframe.status.Status;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ActionItem;
import com.sbkinoko.sbkinokorpg.mapframe.Player;

public class Tool extends ActionItem {
    public Tool(ToolDataAction toolData) {
        super(toolData);
    }

    @Override
    public int getActionType() {
        return BattleConst.Action_Tool;
    }

    static public boolean decrease(int[] items, int usedItemNum) {
        items[usedItemNum] = 0;//アイテムを消去
        for (int i = usedItemNum; i < items.length - 1; i++) {
            items[i] = items[i + 1];
            if (items[i + 1] == 0) {
                return true;
            }
        }
        //最後まで来たので一番下を空欄に
        items[items.length - 1] = 0;
        return true;
    }

    static public boolean decrease(int[][] items, int usedItemNum) {
        items[usedItemNum][1]--;//アイテムを一個減らす
        if (items[usedItemNum][1] == 0) {//アイテムが無くなったら欄をずらす
            for (int i = usedItemNum; i < items.length - 1; i++) {
                items[i][0] = items[i + 1][0];
                items[i][1] = items[i + 1][1];
                if (items[i + 1][0] == 0) {
                    return true;//アイテムを使い切った
                }
            }
            //最後まで来たので一番下を空欄に
            items[items.length - 1][0] = 0;
            items[items.length - 1][1] = 0;
        }
        return false;
    }

    @Override
    public int getManipulatedValue(int status) {
        return getPower();
    }

    boolean isVanish() {
        return ((ToolDataAction) actionItemData).isVanish();
    }

    public boolean doAfterProcess(Status status, Player player, int itemPosition) {
        if (!isVanish()) {
            return false;
        }

        //袋だったら
        if (status == null) {
            return Tool.decrease(player.getHaveItem(), itemPosition);
        }

        if (player == null) {
            status.setLastSelectedTool(0);//アイテムを使ったから先頭を選ぶ
        }

        return Tool.decrease(((PlayerStatus) status).getHaveTool(), itemPosition);

    }

    @Override
    public String getActionTxt(Status nowPlayer) {
        return nowPlayer.getName() + "は\n" + getName() + "を使った";
    }

    public int getPrice() {
        return ((ToolDataAction) actionItemData).getPrice();
    }
}
