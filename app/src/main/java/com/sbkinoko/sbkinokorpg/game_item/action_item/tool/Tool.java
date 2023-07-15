package com.sbkinoko.sbkinokorpg.game_item.action_item.tool;

import com.sbkinoko.sbkinokorpg.battleframe.BattleConst;
import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.battleframe.status.Status;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ActionItem;
import com.sbkinoko.sbkinokorpg.mapframe.Player;
import com.sbkinoko.sbkinokorpg.repository.PlayerToolRepository;

public class Tool extends ActionItem {


    public Tool(ToolDataAction toolData) {
        super(toolData);
    }

    @Override
    public int getActionType() {
        return BattleConst.Action_Tool;
    }

    static public void decreaseBagTool(int[][] items, int usedItemNum) {
        items[usedItemNum][1]--;//アイテムを一個減らす

        //アイテムがなくなっていないので使い切ってない状態にする
        if (items[usedItemNum][1] != 0) {
            LastItemUseUpDate.setIsLastItemUseUp(false);
            return;
        }

        siftTools(items, usedItemNum);
        //Toolを使い切った状態にする
        LastItemUseUpDate.setIsLastItemUseUp(true);
    }

    /**
     * バッグのToolをシフト
     */
    static private void siftTools(int[][] items, int usedItemNum) {
        for (int i = usedItemNum; i < items.length - 1; i++) {
            items[i][0] = items[i + 1][0];
            items[i][1] = items[i + 1][1];
            if (items[i + 1][0] == 0) {
                break;
            }
        }
        //最後まで来たので一番下を空欄に
        items[items.length - 1][0] = 0;
        items[items.length - 1][1] = 0;
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
            decreaseBagTool(mapPlayer.getHaveItem(), itemPosition);
            return;
        }

        if (isInBattle) {
            status.setLastSelectedTool(0);//アイテムを使ったから先頭を選ぶ
        }

        int playerId = ((PlayerStatus) status).getPlayerID();

        PlayerToolRepository.getPlayerToolRepository().decreasePlayerTool(
                playerId,
                itemPosition);
    }

    @Override
    public String getActionTxt(Status nowPlayer) {
        return nowPlayer.getName() + "は\n" + getName() + "を使った";
    }

    public int getPrice() {
        return ((ToolDataAction) actionItemData).getPrice();
    }
}
