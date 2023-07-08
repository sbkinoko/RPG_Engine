package com.sbkinoko.sbkinokorpg.game_item.action_item;

import com.sbkinoko.sbkinokorpg.GameParams;
import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.game_item.action_item.tool.LastItemUseUpDate;
import com.sbkinoko.sbkinokorpg.game_item.action_item.tool.Tool;
import com.sbkinoko.sbkinokorpg.mapframe.Player;
import com.sbkinoko.sbkinokorpg.mapframe.UseUpInfo;

public class ToolGive {
    static private int
            _from_player,
            _to_player;

    public static UseUpInfo giveProcess(int from_player, int to_player, int usedItemPosition,
                                        Player player, PlayerStatus[] status) {
        if (from_player == to_player) {
            return new UseUpInfo("移動しないよ", false);
        }

        _from_player = from_player;
        _to_player = to_player;
        if (give(usedItemPosition, player, status)) {
            checkDecrease(usedItemPosition, player, status);
            return new UseUpInfo(
                    getGiveText(status),
                    LastItemUseUpDate.isLastItemUseUp());
        } else {
            return new UseUpInfo("アイテムがいっぱいです", false);
        }
    }

    /**
     * @param usedItemPosition
     * @return 渡せたらtrue　そうでなければfalse
     */
    static private boolean give(int usedItemPosition, Player player, PlayerStatus[] status) {
        int itemID;

        if (_from_player < GameParams.PLAYER_NUM) {
            itemID = status[_from_player].getHaveTool()[usedItemPosition];
        } else {
            itemID = player.getHaveItem()[usedItemPosition][0];
        }

        if (_to_player < GameParams.PLAYER_NUM) {
            return status[_to_player].addHaveItem(itemID);
        } else {
            player.addItem(itemID, 1);
        }
        return true;
    }

    static private void checkDecrease(int useItemPosition,
                                      Player player,
                                      PlayerStatus[] status) {
        if (_from_player < GameParams.PLAYER_NUM) {
            Tool.decreasePlayerTool(status[_from_player].getHaveTool(), useItemPosition);
        } else {
            Tool.decreaseBagTool(player.getHaveItem(), useItemPosition);
        }
    }

    static private String getGiveText(PlayerStatus[] status) {
        if (_to_player < GameParams.PLAYER_NUM) {
            return status[_to_player].getName()
                    + "に渡した";
        }
        return "袋に入れた";
    }
}
