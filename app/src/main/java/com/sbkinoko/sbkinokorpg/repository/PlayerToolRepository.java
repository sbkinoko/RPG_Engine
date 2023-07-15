package com.sbkinoko.sbkinokorpg.repository;

import com.sbkinoko.sbkinokorpg.GameParams;
import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;

public class PlayerToolRepository {
    private static final PlayerToolRepository playerToolRepository = new PlayerToolRepository();

    private final int[][] playersItemList = new int[GameParams.PLAYER_NUM][PlayerStatus.canHaveToolNum];

    private PlayerToolRepository() {

    }

    public static PlayerToolRepository getPlayerToolRepository() {
        return playerToolRepository;
    }

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


}
