package com.sbkinoko.sbkinokorpg.repository.playertool;

import com.sbkinoko.sbkinokorpg.GameParams;
import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.game_item.action_item.tool.LastItemUseUpDate;

public class PlayerToolRepositoryImpl implements PlayerToolRepository {

    private final int[][] playersItemList =
            new int[GameParams.PLAYER_NUM][PlayerStatus.canHaveToolNum];
    

    public int[] getAllItem(int playerId) {
        //同じものを参照して上書きする可能性を排除するためコピーを返却
        int[] _nowList = new int[PlayerStatus.canHaveToolNum];
        System.arraycopy(
                playersItemList[playerId],
                0, _nowList,
                0, PlayerStatus.canHaveToolNum);
        return _nowList;
    }

    public int getItem(int playerId, int itemPosition) {
        return playersItemList[playerId][itemPosition];
    }

    public void addItem(int playerId, int itemId) {
        int[] playerItemList = playersItemList[playerId];
        for (int i = 0; i < playerItemList.length; i++) {
            if (playerItemList[i] == 0) {
                playerItemList[i] = itemId;
                return;
            }
        }
    }

    public void decreasePlayerTool(int playerId, int usedItemPos) {
        int[] playerItemList = playersItemList[playerId];
        playerItemList[usedItemPos] = 0;//アイテムを消去
        LastItemUseUpDate.setIsLastItemUseUp(true);
        siftPlayerTools(playerItemList, usedItemPos);
    }

    private void siftPlayerTools(int[] items, int usedItemNum) {
        for (int i = usedItemNum; i < items.length - 1; i++) {
            items[i] = items[i + 1];
            if (items[i + 1] == 0) {
                break;
            }
        }
        //最後まで来たので一番下を空欄に
        items[items.length - 1] = 0;
    }

    public boolean canReceiveTool(int playerId) {
        return playersItemList[playerId][PlayerStatus.canHaveToolNum - 1] == 0;
    }
}
